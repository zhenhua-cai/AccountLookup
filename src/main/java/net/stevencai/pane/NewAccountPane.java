package net.stevencai.pane;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import net.stevencai.entity.Account;
import net.stevencai.service.StringProcessUtil;

public class NewAccountPane extends DisplayPane{

    private Button addButton;
    private TextField title;
    private TextField username;
    private TextField password;
    private TextField email;

    public NewAccountPane(){
    }

    @Override
    public Pane createPane(){
        Pane primaryPane = getPrimaryPane();
        createFields(primaryPane);
        return primaryPane;
    }
    private void createFields(Pane pane){
        HBox hBox =new HBox();
        hBox.setAlignment(Pos.CENTER);
        VBox root = new VBox(5);
        hBox.getChildren().add(root);
        title = createField("Account Name: ",root);
        username = createField("Username: ",root);
        password = createField("Password: ",root,true);
        email =createField("Email: ",root);
        addButton = addButton("Confirm", root);
        root.setPadding(new Insets(20));
        VBox.setMargin(root,new Insets(30,20,20,20));
        pane.getChildren().add(hBox);
    }
    private TextField createField(String text, VBox root, boolean hideInputContext){
        HBox row = new HBox(10);
        Label label = new Label(text);
        label.setPrefWidth(200);
        label.setFont(Font.font(20));
        label.setPadding(new Insets(10));
        label.setTextAlignment(TextAlignment.RIGHT);

        TextField field ;
        if(!hideInputContext) {
            field = new TextField();
        }
        else{
            field = new PasswordField();
        }
        field.setPrefWidth(300);
        field.setPadding(new Insets(10));
        HBox.setMargin(field,new Insets(10,0,10,0));
        row.getChildren().addAll(label,field);
        root.getChildren().add(row);
        return field;
    }

    private TextField createField(String text, VBox root){
        return createField(text,root, false);
    }

    private Button addButton(String text, VBox root){
        HBox row = new HBox();
        HBox.setMargin(row,new Insets(20));
        row.setPadding(new Insets(30));
        row.setPrefWidth(root.getPrefWidth());
        row.setAlignment(Pos.CENTER);
        Button addButton = new Button(text);
        addButton.setFont(Font.font(20));
        row.getChildren().add(addButton);
        root.getChildren().add(row);
        return addButton;
    }
    public Account createAccount() {
        Account account = new Account();
        String titleStr = StringProcessUtil.processInputFieldString(this.title.getText());
        if (titleStr.length() == 0) {return null;}
        account.setTitle(title.getText());
        String usernameStr = StringProcessUtil.processInputFieldString(username.getText());
        if (usernameStr.length() == 0) {return null;}
        account.setUsername(usernameStr);

        String passwordStr = StringProcessUtil.processInputFieldString(password.getText());
        if (passwordStr.length() == 0) {return null;}
        account.setPassword(passwordStr);

        String emailStr = StringProcessUtil.processInputFieldString(email.getText());
        account.setEmail(emailStr.length()==0?null:emailStr);

        return account;
    }

    public void setOnButtonClicked(EventHandler<? super MouseEvent> value){
        addButton.setOnMouseClicked(value);
    }
}
