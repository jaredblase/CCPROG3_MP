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

/**
 * The CaseTableViewController class holds the information of all positive cases in the system
 * and sets the columns to be displayed to the information of the positive cases. This class also
 * handles filtering of cases and displaying the information of an individual case.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see Controller
 */
public class CaseTableViewController extends Controller {
    /** The TableView that displays all positive cases. */
    @FXML
    private TableView<Case> tableView;
    /** The case numbers of the positive cases. */
    @FXML
    private TableColumn<Case, Integer> caseNumCol;
    /** The report dates of the positive cases. */
    @FXML
    private TableColumn<Case, Calendar> dateCol;
    /** The usernames of the positive users. */
    @FXML
    private TableColumn<Case, String> usernameCol;
    /** The usernames of the contact tracers assigned to the positive cases. */
    @FXML
    private TableColumn<Case, String> tracerCol;
    /** The statuses of the positive cases. */
    @FXML
    private TableColumn<Case, Character> statusCol;
    @FXML
    private TableColumn<Case, String> severityCol;
    /** The TextField which is used to filter the positive cases by city. */
    @FXML
    private TextField cityFilter;
    /** The ChoiceBox which is used to filter the positive cases by status. */
    @FXML
    private ChoiceBox<Character> statusFilter;
    /** The start date of the date range which is used to filter the positive cases. */
    @FXML
    private DatePicker startFilter;
    /** The end date of the date range which is used to filter the positive cases. */
    @FXML
    private DatePicker endFilter;
    /** The ToggleButton which is used to filter the positive cases by whether they are assigned.*/
    @FXML
    private ToggleButton unassignedOnly;
    /** The Button that, when pressed, filters the positive cases to be displayed
     * based on the filters.*/
    @FXML
    private Button actionButton;
    /** The total number of positive cases. */
    @FXML
    private Label totalCtr;
    /** The total number of positive cases after being filtered. */
    @FXML
    private Label currentCtr;
    /** The MenuController that handles the menu of the user logged in. */
    @FXML
    private MenuController menuController;
    /** The list of positive cases in the system. */
    private ObservableList<Case> cases;
    @FXML
    private ComboBox<String> severity;

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the data and behaviour of the table.
     */
    @FXML
    public void initialize() {
        // initialize statusFilter
        statusFilter.getItems().addAll('P', 'T', '\0');
        severity.getItems().addAll("Asymptomatic", "Mild", "Severe");

        // initialize column data types
        caseNumCol.setCellValueFactory(new PropertyValueFactory<>("caseNum"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        tracerCol.setCellValueFactory(new PropertyValueFactory<>("tracer"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        severityCol.setCellValueFactory(new PropertyValueFactory<>("severity"));

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
        actionButton.setOnAction(e -> {
            filteredList.setPredicate(c -> ((GovOfficial) this.mainController.getUserModel())
                    .filter(c, cityFilter.getText(), statusFilter.getValue() == null ? '\0' : statusFilter.getValue(),
                            startFilter.getValue(), endFilter.getValue(), unassignedOnly.isSelected(), severity.getValue()));
            currentCtr.setText("Current Count: " + filteredList.size());
        });

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
        totalCtr.setText("Total Cases: " + cases.size());
    }
}
