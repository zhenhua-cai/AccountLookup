package net.stevencai.app;

import com.sun.istack.NotNull;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import net.stevencai.entity.Account;
import net.stevencai.entity.User;
import net.stevencai.pane.*;
import net.stevencai.service.LookupService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;

import java.util.List;


public class AccountLookupApp extends Application {

    private boolean showedAll = false;
    private boolean validSearch = false;
    private User user;

    private static final LookupService accountLookupService;
    static{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        accountLookupService = context.getBean("accountLookupService", LookupService.class);
    }

    public AccountLookupApp(){
    }

    @Override
    public void start(Stage stage) {
        Scene scene = getStartScene();
        stage.setScene(scene);

        //set up app title.
        stage.setTitle("Account Lookup");
        stage.setWidth(1050);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * get a scene with login pane.
     * @return a scene
     */
    private Scene getStartScene(){
        LoginPane loginPane = new LoginPane();
        Scene scene = new Scene(loginPane.getPane());
        setActionOnLogin(loginPane,scene);
        return scene;
    }

    public void setActionOnLogin(LoginPane pane, Scene scene){
        pane.setLoginButtonClicked(e->{
            validateUser(pane,scene);
        });
        pane.setActionOnRegisterButtonClicked(e->{
            switchToRegisterPanel(scene);
        });
    }

    private void switchToLoginPanel(Scene scene){
        LoginPane loginPane = new LoginPane();
        scene.setRoot(loginPane.getPane());
        setActionOnLogin(loginPane,scene);
    }

    private void switchToRegisterPanel(Scene scene){
        RegisterUserPane registerUserPane = new RegisterUserPane();
        scene.setRoot(registerUserPane.getPane());
        registerUserPane.setActionOnRegisterButton(event->{
            registerButtonAction(registerUserPane,scene);
        });
        registerUserPane.setActionOnBackToLoginButton(e->{
            switchToLoginPanel(scene);
        });
    }
    private void registerButtonAction(RegisterUserPane registerUserPane, Scene scene){
        User user = createUser(registerUserPane.getUsername(),registerUserPane.getPassword(),registerUserPane.getConfirmedPassword());
        if(user == null){
            return;
        }
        try {
            accountLookupService.saveUser(user);
            swithToDisplayAccountaPane(scene);
            showMessageBox(Alert.AlertType.INFORMATION,"Success!","Congrats!","User created!");
            this.user = user;
        }
        catch(ConstraintViolationException ex){
            showMessageBox(Alert.AlertType.ERROR,"Error!","Sorry! Failed to create user!","Username already exits.");
        }
        catch(Exception ex){
            showMessageBox(Alert.AlertType.ERROR,"Error!","Sorry! Failed to user account!","Unknown issue.");
        }
    }

    /**
     * check if user is able to login.
     * @param pane login pane.
     * @param scene scene
     */
    private void validateUser(LoginPane pane, Scene scene){
        String username = pane.getUserName();
        String password = pane.getPassword();
        if(username.length() == 0|| password.length() == 0){
            return;
        }
        User user = accountLookupService.getUser(username);
        if(user == null || !accountLookupService.comparePassword(password,user.getPassword())){
            showMessageBox(Alert.AlertType.ERROR,"Unable to login","Invalid User","Invalid username or password!");
        }
        else{
            DisplayAccountsPane displayAccountsPane = new DisplayAccountsPane();
            scene.setRoot(displayAccountsPane.createPane());
            setButtonsAction(displayAccountsPane);
            setMenuActions(displayAccountsPane,scene);
            this.user = user;
        }
    }

    /**
     * search account based on account name
     * @param searchContent account name or part of the account name
     * @param pane display pane that contains search area.
     */
    private void searchAccounts(String searchContent,DisplayAccountsPane pane){
        searchAccounts(searchContent,pane,false);
    }

    /**
     * search account based on account name or show all accounts in database
     * @param searchContent account name or part of the account name
     * @param pane display pane that contains search area.
     * @param showAll whether show all accounts, in this case, no need to provide <code>searchContent</code>
     */
    private void searchAccounts(String searchContent, DisplayAccountsPane pane, boolean showAll){
        List<Account> accounts;
        if(!showAll) {
            accounts = accountLookupService.getAccount(searchContent, this.user);
        }
        else{
            accounts = accountLookupService.getAccounts(this.user);
        }
        if(accounts.size() == 0){
            validSearch = false;
        }
        else{
            validSearch = true;
        }
        pane.clearAccountTables();
        pane.addAccountToTable(accounts);
    }

    /**
     * set action on search button
     * @param pane display pane that contains search button.
     */
    private void setSearchButtonAction(DisplayAccountsPane pane){
        pane.setOnSeachAction(click->{
            showedAll = false;
            if(pane.getAccountNameText().length() == 0){
                pane.clearAccountTables();
                return;
            }
            searchAccounts(pane.getAccountNameText(),pane);
        });
    }

    /**
     * set action on refresh button
     * @param pane display pane that contains refresh button.
     */
    private void setRefreshButtonAction(@NonNull DisplayAccountsPane pane){
        pane.setOnRefreshAction(click->{
            if(showedAll){
                searchAccounts(null,pane,true);
            }
            else if(!validSearch || pane.getSearchContent() == null || pane.getSearchContent().length() == 0){
                return;
            }
            else{
                searchAccounts(pane.getSearchContent(),pane);
            }
        });
    }

    /**
     *set action on show all button
     * @param pane display pane that contains showAll button.
     */
    private void setShowAllButtonAction(@NotNull DisplayAccountsPane pane){
        pane.setOnShowAllAction(e->{
            searchAccounts(null,pane,true);
            showedAll = true;
        });
    }

    /**
     * set action on clear button
     * @param pane display pane that contains clear button.
     */
    private void setClearAllButtonAction(@NonNull DisplayAccountsPane pane){
        pane.setOnClearTableAction(e->{
            validSearch =false;
            showedAll = false;
            pane.clearSearch();
        });
    }

    /**
     * set actions on buttons in display pane
     * @param pane display pane that contains buttons.
     */
    private void setButtonsAction(@NotNull DisplayAccountsPane pane){
        setSearchButtonAction(pane);
        setRefreshButtonAction(pane);
        setShowAllButtonAction(pane);
        setClearAllButtonAction(pane);
    }

    /**
     * set action on create new account button in NewAccountPane.
     * show message when add account or fail.
     * @param newAccountPane new account pane
     * @param scene scene that contains newAccountPane
     */
    private void setButtonToAddNewAccount(NewAccountPane newAccountPane,Scene scene){
        newAccountPane.setOnButtonClicked(click->{
            newAccountbuttonAction(newAccountPane,scene);
        });
    }
    private void newAccountbuttonAction(NewAccountPane newAccountPane,Scene scene){
        Account account = newAccountPane.createAccount();
        if(account == null){return;}
        try {
            account.setUser(this.user);
            accountLookupService.saveAccount(account);
            swithToDisplayAccountaPane(scene);
            showMessageBox(Alert.AlertType.INFORMATION,"Success!","Congrats!","Successfully added/updated account!");
        }
        catch(ConstraintViolationException ex){
            showMessageBox(Alert.AlertType.ERROR,"Error!","Sorry! Failed to added/updated account!","Account already exits.");
        }
        catch(Exception ex){
            showMessageBox(Alert.AlertType.ERROR,"Error!","Sorry! Failed to added/updated account!",ex.getMessage());
        }
    }
    /**
     * switch display panel in scene
     * @param scene scene
     */
    private void swithToDisplayAccountaPane(Scene scene){
        DisplayAccountsPane displayPane = new DisplayAccountsPane();
        scene.setRoot(displayPane.createPane());
        setButtonsAction(displayPane);
        setMenuActions(displayPane, scene);
    }

    /**
     * set actions on menus
     * @param pane display pane that contains menu.
     * @param scene the scene that contains display pane.
     */
    public void setMenuActions(DisplayPane pane,Scene scene){
        //set action on New Account menu item.
        pane.setOnNewAccount(e->{
            NewAccountPane newAccountPane = new NewAccountPane();
            scene.setRoot(newAccountPane.createPane());
            setButtonToAddNewAccount(newAccountPane,scene);
            setMenuActions(newAccountPane,scene);
        });

        //set action on Search Account menu item.
        pane.setOnSearchAccount(e->{
            DisplayAccountsPane displayAccountsPane = new DisplayAccountsPane();
            scene.setRoot(displayAccountsPane.createPane());
            setButtonsAction(displayAccountsPane);
            setMenuActions(displayAccountsPane,scene);
        });

        //set action on Delete Account menu item.
        pane.setOnDeleteAccount(e->{
            if(pane instanceof DisplayAccountsPane){
                deleteAccount((DisplayAccountsPane) pane);
            }
        });

        //set action on Update Account menu item.
        pane.setOnUpdateAccount(e->{
            if(pane instanceof DisplayAccountsPane){
                Account account = ((DisplayAccountsPane)pane).updateSelectedRow();
                if(account == null){return;}
                try {
                    accountLookupService.saveAccount(account);
                    showMessageBox(Alert.AlertType.INFORMATION,"Success!","Congrats!","Successfully updated account!");
                }
                catch(Exception ex){
                    showMessageBox(Alert.AlertType.ERROR,"Error!",
                            "Sorry! Failed to update account!",ex.getMessage());
                }
            }
        });

        //set action on logout user.
        pane.setOnLogoutUser(e->{
            switchToLoginPanel(scene);
            this.user = null;
        });
    }

    /**
     * delete selected account in display pane.
     * @param pane display pane
     */
    private void deleteAccount(DisplayAccountsPane pane){
        Account account = pane.deleteSelectedRow();
        if(account == null){
            return;
        }
        try {
            accountLookupService.deleteAccount(account);
            showMessageBox(Alert.AlertType.INFORMATION,"Success!","Congrats!","Successfully deleted account!");
        }
        catch(Exception ex){
            showMessageBox(Alert.AlertType.ERROR,"Error!",
                    "Sorry! Failed to delete account!",ex.getMessage());
        }
    }

    /**
     * show a message box to user.
     * @param type Alert type.
     * @param title message title
     * @param header message header
     * @param message message content
     */
    private void showMessageBox(Alert.AlertType type, String title, String header, String message){
        Alert alert= new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private User createUser(String username, String password, String confirmedPassword){
        if(!validateUsername(username)){
            return null;
        }
        if(!validatePassword(password,confirmedPassword)){
            return null;
        }
        return  new User(username, password);
    }

    /**
     * validate password. must have more than 6 and less than 30 characters.
     * can only contains letter, digit, special chars<code>.@&*#,:;</code> characters
     * @param password password
     * @param confirmedPassword confirmed password
     * @return true if valid.
     */
    private boolean validatePassword(String password, String confirmedPassword){

        if(password== null || password.length() == 0){
            showMessageBox(Alert.AlertType.ERROR, "Error","Invalid password","Password cannot be empty");
            return false;
        }

        if(!password.equals(confirmedPassword)){
            showMessageBox(Alert.AlertType.ERROR, "Error","Password not match!","password not match");
            return false;
        }
        if(password.length() < 6){
            showMessageBox(Alert.AlertType.ERROR, "Error","Invalid password","Password cannot have less than 6 characters");
            return false;
        }
        if(password.length() > 30){
            showMessageBox(Alert.AlertType.ERROR, "Error","Invalid password","Password cannot have more than 30 characters");
            return false;
        }
        String punctuations = ".@&*#,:;";
        for(int i =0;i<password.length();i++){
            char c = password.charAt(i);
            if(!Character.isLetterOrDigit(c) && !isSpecitalChar(c)){
                showMessageBox(Alert.AlertType.ERROR, "Error","Invalid password",
                        "Password contains invalid characters." +
                                "\nOnly letters, digits, "+punctuations+" are allowed.");
                return false;
            }
        }
        return true;
    }
    private boolean isSpecitalChar(char c){
        switch(c){
            case '.':
            case ',':
            case ':':
            case ';':
            case '@':
            case '&':
                return true;
            default:
                return false;
        }
    }
    /**
     * validate username.
     * @param username username
     * @return true if valid.
     */
    private boolean validateUsername(String username){
        if(username== null || username.length() == 0){
            showMessageBox(Alert.AlertType.ERROR, "Error","Invalid username","username cannot be empty");
            return false;
        }
        if(username.length() >= 25){
            showMessageBox(Alert.AlertType.ERROR, "Error","Invalid username","Username cannot have more than 25 characters");
            return false;
        }
        for(int i = 0;i<username.length();i++){
            if(!Character.isLetterOrDigit(username.charAt(i))){
                showMessageBox(Alert.AlertType.ERROR, "Error","Invalid username","Username can only contains letters or digits");
                return false;
            }
        }
        return true;
    }
    /**
     * Run Account Lookup app.
     * @param args arguments from command line.
     */
    public static void run(String... args){
        launch(args);
    }
}
