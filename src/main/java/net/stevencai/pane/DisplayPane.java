package net.stevencai.pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

public abstract class DisplayPane {
    private ManuPane menuPane;

    public DisplayPane(){
        menuPane = new ManuPane();
    }

    public Pane getPrimaryPane(){
        return menuPane.getPrimaryPane();
    }

    public void setOnNewAccount(EventHandler<ActionEvent> value){
        menuPane.setOnNewAccount(value);
    }
    public void setOnSearchAccount(EventHandler<ActionEvent> value){
        menuPane.setOnSearchAccount(value);
    }
    public void setOnUpdateAccount(EventHandler<ActionEvent> value){
        menuPane.setOnUpdateAccount(value);
    }
    public void setOnDeleteAccount(EventHandler<ActionEvent> value){
        menuPane.setOnDeleteAccount(value);
    }
    public abstract Pane createPane();

}
