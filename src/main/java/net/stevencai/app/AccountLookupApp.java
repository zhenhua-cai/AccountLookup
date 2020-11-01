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
        setSearchButtionAction(pane);
        setMenuActions(pane,scene);
        return scene;
    }
    private void setSearchButtionAction(DisplayAccountsPane pane){
        pane.setOnclickAction(click->{
            List<Account> accounts = accountLookupService.getAccount(pane.getAccountNameText());
            pane.clearAccountTables();
            pane.addAccountToTable(accounts);
        });
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
                    setSearchButtionAction(displayPane);
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
            setSearchButtionAction(displayAccountsPane);
            setMenuActions(displayAccountsPane,scene);
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
