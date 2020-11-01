package net.stevencai.pane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import net.stevencai.entity.Account;
import net.stevencai.service.StringProcessUtil;

import java.time.LocalDateTime;
import java.util.List;

public class DisplayAccountsPane extends DisplayPane {

    private TextField accountName;
    private Button searchButton;
    private TableView<Account> table;

    private ObservableList<Account> accounts= FXCollections.observableArrayList();

    public DisplayAccountsPane(){

    }

    @Override
    public Pane createPane(){
        Pane pane = getPrimaryPane();
        HBox searchArea = creatSearchArea();
        Node resultAre = creatResultArea();
        pane.getChildren().addAll(searchArea,resultAre);
        return pane;
    }

    private HBox creatSearchArea(){
        HBox searchArea = new HBox();
        searchArea.setAlignment(Pos.CENTER);
        searchArea.setSpacing(5);
        //account label

        Label accountLabel= createLabel("Account Name: ");

        //account text field
        accountName= createSearchBox();
        accountName.setPrefWidth(300);

        //search button
        searchButton = new Button("Search");
        searchButton.setPadding(new Insets(10));
        HBox.setMargin(searchButton,new Insets(10));

        searchArea.getChildren().addAll(accountLabel,accountName,searchButton);

        return searchArea;
    }
    private TextField createSearchBox(){
        TextField searchBox = new TextField();
        searchBox.setPadding(new Insets(10));
        HBox.setMargin(searchBox,new Insets(10,0,10,0));
        return searchBox;
    }

    private Label createLabel(String text){
        Label label = new Label(text);
        label.setFont(Font.font(20));
        label.setPadding(new Insets(10));
        label.setTextAlignment(TextAlignment.CENTER);
        HBox.setMargin(label,new Insets(0,0,10,0));
        return label;
    }

    private TableView<Account> creatResultArea(){
        table = new TableView<>();
        table.setItems(accounts);

        TableColumn<Account,String> account= createTableColumn("Account Name");
        account.setCellValueFactory(new PropertyValueFactory<Account,String>("title"));

        TableColumn<Account,String> username = createTableColumn("Username");
        username.setCellValueFactory(new PropertyValueFactory<Account,String>("Username"));

        TableColumn<Account,String> password = createTableColumn("Password");
        password.setCellValueFactory(new PropertyValueFactory<Account,String>("Password"));

        TableColumn<Account,String> email = createTableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Account,String>("Email"));

        TableColumn<Account, LocalDateTime> lastUpdatedTime = new TableColumn<>("Last Updated Time");
        lastUpdatedTime.setPrefWidth(210);
        lastUpdatedTime.setResizable(false);
        lastUpdatedTime.setCellValueFactory(new PropertyValueFactory<Account,LocalDateTime>("lastUpdatedTime"));

        table.getColumns().addAll(account,username,password,email,lastUpdatedTime);
        return table;
    }

    private TableColumn<Account,String> createTableColumn(String text){
        TableColumn<Account,String> column= new TableColumn<>(text);
        column.setPrefWidth(210);
        column.setResizable(false);
        return column;
    }

    public Account deleteSelectedRow(){
        Account selectedItem = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selectedItem);
        return selectedItem;
    }

    public void setOnclickAction(EventHandler<? super MouseEvent> value){
        searchButton.setOnMouseClicked(value);
    }
    public String getAccountNameText(){
        return StringProcessUtil.processInputFieldString(accountName.getText());
    }
    public void addAccountToTable(Account account){
        accounts.add(account);
    }
    public void addAccountToTable(List<Account> accounts){
        this.accounts.addAll(accounts);
    }
    public void clearAccountTables(){
        accounts.clear();
    }
}
