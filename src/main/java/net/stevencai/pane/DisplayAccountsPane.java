package net.stevencai.pane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import net.stevencai.entity.Account;
import net.stevencai.service.StringProcessUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DisplayAccountsPane extends DisplayPane {

    private TextField accountName;
    private Button searchButton;
    private Button refreshButton;
    private TableView<Account> table;
    private String searchContent;
    private Button showAll;

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

        //refresh button
        refreshButton = new Button("Refresh");
        refreshButton.setPadding(new Insets(10));
        HBox.setMargin(refreshButton,new Insets(10));

        //showAll button
        showAll = new Button("Show All");
        showAll.setPadding(new Insets(10));
        HBox.setMargin(showAll,new Insets(10));

        searchArea.getChildren().addAll(accountLabel,accountName,searchButton,refreshButton,showAll);

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
        table.setEditable(true);
        table.setItems(accounts);

        TableColumn<Account,String> account= createTableColumn("Account Name");
        account.setCellValueFactory(new PropertyValueFactory<Account,String>("title"));

        TableColumn<Account,String> username = createTableColumn("Username");
        username.setCellValueFactory(new PropertyValueFactory<Account,String>("Username"));
        username.setCellFactory(TextFieldTableCell.forTableColumn());
        username.setOnEditCommit(e->{
            table.getItems().get(e.getTablePosition().getRow()).setUsername(e.getNewValue());
        });

        TableColumn<Account,String> password = createTableColumn("Password");
        password.setCellValueFactory(new PropertyValueFactory<Account,String>("Password"));
        password.setCellFactory(TextFieldTableCell.forTableColumn());
        password.setOnEditCommit(e->{
            table.getItems().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
        });

        TableColumn<Account,String> email = createTableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Account,String>("Email"));
        email.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setOnEditCommit(e->{
            table.getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });

        TableColumn<Account, LocalDateTime> lastUpdatedTime = new TableColumn<>("Last Updated Time");
        lastUpdatedTime.setPrefWidth(210);
        lastUpdatedTime.setResizable(false);
        lastUpdatedTime.setCellValueFactory(new PropertyValueFactory<Account,LocalDateTime>("lastUpdatedTime"));
        lastUpdatedTime.setCellFactory(new LocalDateTimeFormatter<Account,LocalDateTime>());

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

    public Account updateSelectedRow(){
        return table.getSelectionModel().getSelectedItem();
    }

    public void setOnSeachAction(EventHandler<? super MouseEvent> value){
        searchButton.setOnMouseClicked(value);
    }

    public void setOnRefreshAction(EventHandler<? super MouseEvent> value){
        refreshButton.setOnMouseClicked(value);
    }

    public void setOnShowAllAction(EventHandler<? super MouseEvent> value){
        showAll.setOnMouseClicked(value);
    }

    public String getAccountNameText(){
        searchContent = StringProcessUtil.processInputFieldString(accountName.getText());
        return searchContent;
    }
    public String getSearchContent(){
        return searchContent;
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

    private class LocalDateTimeFormatter<S,T> implements Callback<TableColumn<S,T>, TableCell<S,T>> {

        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss");

        @Override
        public TableCell<S, T> call(TableColumn<S, T> param) {
            return new TableCell<S,T>(){
                @Override
                protected void updateItem(T item, boolean empty){
                    super.updateItem(item,empty);
                    if(item == null || empty){
                        setGraphic(null);
                    }
                    else{
                        setGraphic(new Label(((LocalDateTime)item).format(formatter)));
                    }
                }
            };
        }
    }
}
