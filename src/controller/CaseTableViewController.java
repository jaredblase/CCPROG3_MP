package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import model.Case;
import model.GovOfficial;
import model.UserSystem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CaseTableViewController extends Controller {
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
    private ToggleButton unassignedOnly;
    @FXML
    private Button actionButton;
    @FXML
    private MenuController menuController;
    private ObservableList<Case> cases;

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the data and behaviour of the table.
     */
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

        // set rows to display a dialog when double clicked
        tableView.setRowFactory(tv -> {
            TableRow<Case> row = new TableRow<>();

            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    Case c = row.getItem();

                    // stage (dialog) setup
                    Stage stage = new Stage();
                    stage.setTitle("Case " + c.getCaseNum() + " Information");
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.centerOnScreen();

                    // load case information
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/view/Case Information.fxml"));
                        stage.setScene(new Scene(loader.load()));

                        // setup controller
                        ((CaseInformationController) loader.getController()).setModel(c);
                        ((DialogController) loader.getController()).setUser(mainController.getUserModel());

                        stage.showAndWait();                                                // display dialog
                        update();                                                           // update table view
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            });

            return row;
        });

        // format display for the Report Date column
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

        // when filter button is pressed
        actionButton.setOnAction(e -> filteredList.setPredicate(c -> ((GovOfficial) this.mainController.getUserModel())
                .filter(c, cityFilter.getText(), statusFilter.getValue() == null? '\0' : statusFilter.getValue(),
                        startFilter.getValue(), endFilter.getValue(), unassignedOnly.isSelected()))
        );

        unassignedOnly.setOnAction(e -> actionButton.fire());

        SortedList<Case> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    /**
     * Sets up the main controller to the menu controller and retrieves the cases
     * from the user system.
     */
    @Override
    protected void update() {
        menuController.setMainController(mainController);
        cases.setAll(UserSystem.getCases());
    }
}
