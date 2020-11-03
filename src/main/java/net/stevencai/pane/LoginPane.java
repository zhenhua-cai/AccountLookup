package net.stevencai.pane;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import net.stevencai.service.StringProcessUtil;

public class LoginPane {

    private Button login;
    private Button register;
    private TextField usernameField;
    private TextField passwordField;
    private GridPane grid;

    public GridPane getPane() {
        grid = getRoot();
        createLoginForm(grid);
        return grid;
    }

    /**
     * create a login form
     *
     * @param grid parent pane.
     */
    private void createLoginForm(GridPane grid) {
        Text title = new Text("Welcome");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);

        Label userName = new Label("User Name: ");
        grid.add(userName, 0, 1);

        usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        Label pw = new Label("Password: ");
        grid.add(pw, 0, 2);

        passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        register = new Button("Register");
        login = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(register, login);
        grid.add(hbBtn, 1, 4);
    }

    /**
     * create the root pane.
     *
     * @return grid pane.
     */
    private GridPane getRoot() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        return grid;
    }

    public String getUserName() {
        return StringProcessUtil.processInputFieldString(usernameField.getText());
    }

    public String getPassword() {
        return StringProcessUtil.processInputFieldString(passwordField.getText());
    }

    public void setLoginButtonClicked(EventHandler<? super MouseEvent> value) {
        login.setOnMouseClicked(value);
    }

    public void setActionOnRegisterButtonClicked(EventHandler<? super MouseEvent> value) {
        register.setOnMouseClicked(value);
    }
}
