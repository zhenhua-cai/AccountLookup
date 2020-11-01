package net.stevencai.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import net.stevencai.entity.Account;
import net.stevencai.pane.DisplayAccountsPane;
import net.stevencai.pane.DisplayPane;
import net.stevencai.pane.NewAccountPane;
import net.stevencai.service.LookupService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class AccountLookupApp extends Application {

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

        stage.setTitle("Account Lookup");
        stage.setResizable(false);
        stage.show();
    }

    private Scene getStartScene(){
        DisplayAccountsPane pane = new DisplayAccountsPane();

        Scene scene = new Scene(pane.createPane());
        setButtonsAction(pane);
        setMenuActions(pane,scene);
        return scene;
    }
    private void searchAccounts(String searchContent,DisplayAccountsPane pane){
        List<Account> accounts = accountLookupService.getAccount(searchContent);
        pane.clearAccountTables();
        pane.addAccountToTable(accounts);
    }
    private void setSearchButtionAction(DisplayAccountsPane pane){
        pane.setOnSeachAction(click->{
            if(pane.getAccountNameText().length() == 0){
                pane.clearAccountTables();
                return;
            }
            searchAccounts(pane.getAccountNameText(),pane);
        });
    }
    private void setRefreshButtonAction(DisplayAccountsPane pane){
        pane.setOnRefreshAction(click->{
            System.out.println(pane.getSearchContent());
            if(pane.getSearchContent() == null || pane.getSearchContent().length() == 0){
                return;
            }
            searchAccounts(pane.getSearchContent(),pane);
        });
    }
    private void setButtonsAction(DisplayAccountsPane pane){
        setSearchButtionAction(pane);
        setRefreshButtonAction(pane);
    }
    public void setMenuActions(DisplayPane pane,Scene scene){
        pane.setOnNewAccount(e->{
            NewAccountPane newAccountPane = new NewAccountPane();
            scene.setRoot(newAccountPane.createPane());
            newAccountPane.setOnButtonClicked(click->{
                Account account = newAccountPane.createAccount();
                if(account == null){return;}
                try {
                    accountLookupService.saveAccount(account);
                    DisplayAccountsPane displayPane = new DisplayAccountsPane();
                    scene.setRoot(displayPane.createPane());
                    setButtonsAction(displayPane);
                    setMenuActions(displayPane, scene);
                    showMessageBox(Alert.AlertType.INFORMATION,"Success!","Congrats!","Successfully added/updated account!");
                }
                catch(ConstraintViolationException ex){
                    showMessageBox(Alert.AlertType.ERROR,"Error!","Sorry! Failed to added/updated account!","Account Name already exits.");
                }
                catch(Exception ex){
                    showMessageBox(Alert.AlertType.ERROR,"Error!","Sorry! Failed to added/updated account!",ex.getMessage());
                }
            });
            setMenuActions(newAccountPane,scene);
        });
        pane.setOnSearchAccount(e->{
            DisplayAccountsPane displayAccountsPane = new DisplayAccountsPane();
            scene.setRoot(displayAccountsPane.createPane());
            setButtonsAction(displayAccountsPane);
            setMenuActions(displayAccountsPane,scene);
        });

        pane.setOnDeleteAccount(e->{
            if(pane instanceof DisplayAccountsPane){
                Account account = ((DisplayAccountsPane)pane).deleteSelectedRow();
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
        });

        pane.setOnUpdateAccount(e->{
            if(pane instanceof DisplayAccountsPane){
                Account account = ((DisplayAccountsPane)pane).updateSelectedRow();
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
    }
    private void showMessageBox(Alert.AlertType type, String title, String header, String message){
        Alert alert= new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * Run Account Lookup app.
     * @param args arguments from command line.
     */
    public static void run(String... args){
        launch(args);
    }
}
