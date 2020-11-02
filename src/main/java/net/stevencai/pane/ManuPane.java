package net.stevencai.pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ManuPane {
    private VBox primaryPane;
    private MenuBar menuBar;

    public ManuPane() {
        createPane();
    }

    private Pane createPane(){
        primaryPane = new VBox();
        createMenuBar();
        primaryPane.getChildren().addAll(menuBar);
        return primaryPane;
    }
    private void createMenuBar(){
        menuBar = new MenuBar();
        Menu menu = new Menu("File");

        //new Account
        MenuItem newAccount =new MenuItem("New Account");
        menuBar.getMenus().add(menu);
        menu.getItems().add(newAccount);

        //search account
        MenuItem searchAccount = new MenuItem("Search Account");
        menu.getItems().add(searchAccount);

        //update account
        MenuItem updateAccount = new MenuItem("Update Account");
        menu.getItems().add(updateAccount);

        //delete account
        MenuItem deleteAccount = new MenuItem("Delete Account");
        menu.getItems().add(deleteAccount);
    }

    public MenuBar getMenuBar(){
        return menuBar;
    }

    public void setOnNewAccount(EventHandler<ActionEvent> value){
        menuBar.getMenus().get(0).getItems().get(0).setOnAction(value);
    }
    public void setOnSearchAccount(EventHandler<ActionEvent> value){
        menuBar.getMenus().get(0).getItems().get(1).setOnAction(value);
    }
    public void setOnUpdateAccount(EventHandler<ActionEvent> value){
        menuBar.getMenus().get(0).getItems().get(2).setOnAction(value);
    }
    public void setOnDeleteAccount(EventHandler<ActionEvent> value){
        menuBar.getMenus().get(0).getItems().get(3).setOnAction(value);
    }

    public VBox getPrimaryPane() {
        return primaryPane;
    }

    public void setPrimaryPane(VBox primaryPane) {
        this.primaryPane = primaryPane;
    }
}
