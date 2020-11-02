package net.stevencai.pane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.*;
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
    private Button clearTable;
    private TableView<Account> table;
    private String searchContent;
    private Button showAll;

    //contains search results that will be displayed in table view.
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

    /**
     * create searching fields.
     * @return the searching area root.
     */
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

        //showAll button
        showAll = new Button("Show All");
        showAll.setPadding(new Insets(10));
        HBox.setMargin(showAll,new Insets(10));

        //refresh button
        refreshButton = new Button("Refresh");
        refreshButton.setPadding(new Insets(10));
        HBox.setMargin(refreshButton,new Insets(10));

        //showAll button
        clearTable = new Button("Clear");
        clearTable.setPadding(new Insets(10));
        HBox.setMargin(clearTable,new Insets(10));

        searchArea.getChildren().addAll(accountLabel,accountName,searchButton,showAll,refreshButton,clearTable);

        return searchArea;
    }

    /**
     * create text field
     * @return text field
     */
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

        //account name, not editable
        TableColumn<Account,String> account= createTableColumn("Account Name");
        account.setCellValueFactory(new PropertyValueFactory<Account,String>("title"));
        account.setCellFactory(p->new EditCell());

        //user name
        TableColumn<Account,String> username = createTableColumn("Username");
        username.setCellValueFactory(new PropertyValueFactory<Account,String>("Username"));
        username.setCellFactory(p->new EditCell());
        username.setOnEditCommit(e->{
            table.getItems().get(e.getTablePosition().getRow()).setUsername(e.getNewValue());
        });

        //password
        TableColumn<Account,String> password = createTableColumn("Password");
        password.setCellValueFactory(new PropertyValueFactory<Account,String>("Password"));
        password.setCellFactory(p->new PasswordEditCell());
        password.setOnEditCommit(e->{
            table.getItems().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
        });

        //email
        TableColumn<Account,String> email = createTableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Account,String>("Email"));
        email.setCellFactory(p->new EditCell());
        email.setOnEditCommit(e->{
            table.getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });

        //last updated time.
        TableColumn<Account, LocalDateTime> lastUpdatedTime =createTableDataTimeColumn("Last Updated Time");
        lastUpdatedTime.setCellValueFactory(new PropertyValueFactory<Account,LocalDateTime>("lastUpdatedTime"));
        lastUpdatedTime.setCellFactory(new LocalDateTimeFormatter());

        table.getColumns().addAll(account,username,password,email,lastUpdatedTime);
        return table;
    }

    private TableColumn<Account, LocalDateTime> createTableDataTimeColumn(String text){
        TableColumn<Account, LocalDateTime> lastUpdatedTime = new TableColumn<>(text);
        lastUpdatedTime.setPrefWidth(210);
        lastUpdatedTime.setResizable(false);
        return lastUpdatedTime;
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

    public void setOnClearTableAction(EventHandler<? super MouseEvent> value){
        clearTable.setOnMouseClicked(value);
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
    public void clearSearch(){
        clearAccountTables();
        searchContent = null;
    }

    private class LocalDateTimeFormatter implements Callback<TableColumn<Account,LocalDateTime>, TableCell<Account,LocalDateTime>> {

        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss");

        @Override
        public TableCell<Account, LocalDateTime> call(TableColumn<Account, LocalDateTime> param) {
            return new TableCell<Account,LocalDateTime>(){
                @Override
                protected void updateItem(LocalDateTime item, boolean empty){
                    super.updateItem(item,empty);
                    if(item == null || empty){
                        setGraphic(null);
                    }
                    else{
                        setGraphic(new Label(item.format(formatter)));
                    }
                }

            };
        }
    }

    private class PasswordEditCell extends EditCell{
        @Override
        public void updateItem(String item, boolean empty){
            super.updateItem(item,empty);
            if(!empty && !isEditing()){
                setText("******");
                setGraphic(null);
            }
        }
    }
    /**
     * Thanks to the this author:
     *      https://gist.github.com/james-d/be5bbd6255a4640a5357#file-editcell-java-L109
     */
    private class EditCell extends TableCell<Account, String>{
        private TextField textField;
        public EditCell(){
            createContextMenu();
        }
        @Override
        public void startEdit(){
            super.startEdit();
            if(textField == null){
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();

            /*resolve focus issue:
            *  need to click 3 times to gain the focus.
            */
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textField.requestFocus();
                }
            });
        }

        @Override
        public void cancelEdit(){
            super.cancelEdit();
            setText(getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty){
            super.updateItem(item,empty);
            if(empty){
                setText(null);
                setGraphic(null);
            }
            else{
                if(isEditing()){
                    if(textField != null){
                        textField.setText(getItem());
                    }
                    setText(null);
                    setGraphic(textField);
                }
                else{
                    setText(getItem());
                    setGraphic(null);
                }
            }
        }
        @Override
        public void commitEdit(String item){
            if(!isEditing() && !item.equals(getItem())){
                TableView<Account> table = getTableView();
                if(table != null){
                    TableColumn<Account, String> column = getTableColumn();
                    TableColumn.CellEditEvent<Account,String> event = new TableColumn.CellEditEvent<>(
                            table, new TablePosition<Account,String>(table,getIndex(), column),
                            TableColumn.editCommitEvent(),item
                    );
                    Event.fireEvent(column, event);
                }
            }
            super.commitEdit(item);
        }

        private void createTextField(){
            textField = new TextField(getItem());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyReleased(event -> {
                if(event.getCode() == KeyCode.ENTER){
                    commitEdit(textField.getText());
                }
                else if(event.getCode() == KeyCode.ESCAPE){
                    cancelEdit();
                }
            });
            textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if(!newVal){
                    commitEdit(textField.getText());
                }
            });
        }

        /**
         * create context menu. which is used to copy content.
         */
        private void createContextMenu(){
            ContextMenu contextMenu = new ContextMenu();
            MenuItem copy = new MenuItem("Copy");
            copy.setOnAction(e->{
                    ClipboardContent content = new ClipboardContent();
                    content.putString(getItem());
                    Clipboard.getSystemClipboard().setContent(content);
            });
            contextMenu.getItems().add(copy);
            this.setContextMenu(contextMenu);
        }
    }
}
