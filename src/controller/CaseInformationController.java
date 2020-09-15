package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Case;
import model.GovOfficial;
import model.UserSystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CaseInformationController extends Controller {
    @FXML
    private TableView<Case> tableView;
    @FXML
    private TableColumn<Case, Integer> caseNumCol;
    @FXML
    private TableColumn<Case, Calendar> dateCol;
    @FXML
    private TableColumn<Case, String> usernameCol;
    @FXML
    private TableColumn<Case, String> tracerCol;
    @FXML
    private TableColumn<Case, Character> statusCol;
    @FXML
    private TextField cityFilter;
    @FXML
    private ChoiceBox<Character> statusFilter;
    @FXML
    private DatePicker startFilter;
    @FXML
    private DatePicker endFilter;
    @FXML
    private Button actionButton;
    @FXML
    private MenuController menuController;
    private ObservableList<Case> cases;

    @Override
    public void update() {
        menuController.setMainController(mainController);
        cases.setAll(UserSystem.getCases());
    }

    @FXML
    public void initialize() {
        // initialize statusFilter
        statusFilter.getItems().addAll('P', 'T', '\0');

        // initialize column data types
        caseNumCol.setCellValueFactory(new PropertyValueFactory<>("caseNum"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        tracerCol.setCellValueFactory(new PropertyValueFactory<>("tracer"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // format display with Calendar column
        dateCol.setCellFactory(column -> new TableCell<>() {
            private final SimpleDateFormat format = new SimpleDateFormat(" MM/dd/yyyy ");

            @Override
            protected void updateItem(Calendar date, boolean isEmpty) {
                super.updateItem(date, isEmpty);
                if (isEmpty) {
                    setText(null);
                } else {
                    setText(format.format(date.getTime()));
                }
            }
        });

        cases = FXCollections.observableArrayList();
        FilteredList<Case> filteredList = new FilteredList<>(cases, p -> true);

        actionButton.setOnAction(e -> filteredList.setPredicate(c -> ((GovOfficial) this.mainController.getUserModel())
                    .filter(c, cityFilter.getText(), statusFilter.getValue() == null? '\0' : statusFilter.getValue(),
                            startFilter.getValue(), endFilter.getValue()))
        );

        SortedList<Case> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }
}
