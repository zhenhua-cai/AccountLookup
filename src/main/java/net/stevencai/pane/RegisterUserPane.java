package net.stevencai.pane;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import net.stevencai.entity.User;
import net.stevencai.service.StringProcessUtil;

public class RegisterUserPane {
    private GridPane grid;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Button register;

    public GridPane getPane(){
        grid = getRoot();
        createForms(grid);
        return grid;
    }

    /**
     * create a login form
     * @param grid parent pane.
     */
    private void createForms(GridPane grid){

        Label userName = new Label("User Name: ");
        grid.add(userName,0,1);

        usernameField = new TextField();
        grid.add(usernameField,1,1);

        Label pw = new Label("Password: ");
        grid.add(pw,0,2);

        passwordField = new PasswordField();
        grid.add(passwordField, 1,2);

        Label confirmPw= new Label("Confirm Password: ");
        grid.add(confirmPw, 0, 3);

        confirmPasswordField = new PasswordField();
        grid.add(confirmPasswordField, 1,3);

        register = new Button("Register");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(register);

        grid.add(hbBtn,1,4);
    }

    /**
     * create the root pane.
     * @return grid pane.
     */
    private GridPane getRoot(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        return grid;
    }
    public String getUsername(){
        return StringProcessUtil.processInputFieldString(usernameField.getText());
    }
    public String getPassword(){
        return StringProcessUtil.processInputFieldString(passwordField.getText());
    }
    public String getConfirmedPassword(){
        return StringProcessUtil.processInputFieldString(confirmPasswordField.getText());
    }
    public void setActionOnRegisterButton(EventHandler<? super MouseEvent> value){
        register.setOnMouseClicked(value);
    }
}
