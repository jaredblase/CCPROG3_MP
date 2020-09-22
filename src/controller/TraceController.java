package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.util.Pair;

import model.Tracer;
import model.Visit;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TraceController extends Controller {
    /** The ComboBox that contains the case number of the assigned positive cases. */
    @FXML
    private ComboBox<Integer> caseNumber;
    /** The TableView that displays the contacts of a positive case. */
    @FXML
    private TableView<Pair<String, Visit>> tableView;
    /** The usernames of the contacts of a positive case. */
    @FXML
    private TableColumn<Pair<String, Visit>, String> usernameCol;
    /** The establishment codes that indicate where the contacts may have been infected. */
    @FXML
    private TableColumn<Pair<String, Visit>, String> estCodeCol;
    /** The dates when the contacts may have been infected. */
    @FXML
    private TableColumn<Pair<String, Visit>, Calendar> dateCol;
    /** The MenuController that handles the menu of the user logged in. */
    @FXML
    private MenuController menuController;

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the data and behaviour of the table.
     */
    @FXML
    public void initialize() {
        usernameCol.setCellValueFactory(new PairKeyFactory());
        estCodeCol.setCellValueFactory(new PairValueStringFactory());
        dateCol.setCellValueFactory(new PairValueCalendarFactory());

        // format display for the Contact Date column
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
    }

    /**
     * Sets up the main controller to the menu controller and retrieves the case numbers
     * of the assigned positive cases.
     */
    @Override
    public void update() {
        menuController.setMainController(mainController);

        init();
    }

    /**
     * Retrieves the case numbers of the assigned positive cases and sets them to the combo box.
     */
    private void init() {
        ObservableList<Integer> caseNums = FXCollections.observableArrayList();

        Tracer tracer = (Tracer) mainController.getUserModel();
        caseNums.addAll(tracer.getAssigned());

        caseNumber.setItems(caseNums);
    }

    /**
     * Handles the event where a certain positive case is traced.
     */
    @FXML
    public void onTraceAction() {
        ObservableList<Pair<String, Visit>> contacts = FXCollections.observableArrayList();
        if (caseNumber.getValue() != null) {
            Tracer tracer = (Tracer) mainController.getUserModel();
            tracer.trace(caseNumber.getValue(), contacts);
            tableView.setItems(contacts);

            // remove case number from choices
            init();
        }
    }
}

class PairKeyFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Visit>, String>, ObservableValue<String>> {
    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, Visit>, String> data) {
        return new ReadOnlyObjectWrapper<>(data.getValue().getKey());
    }
}

class PairValueStringFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Visit>, String>, ObservableValue<String>> {
    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, Visit>, String> data) {
        String value = data.getValue().getValue().getEstCode();
        return new ReadOnlyObjectWrapper<>(value);
    }
}

class PairValueCalendarFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Visit>, Calendar>, ObservableValue<Calendar>> {
    @Override
    public ObservableValue<Calendar> call(TableColumn.CellDataFeatures<Pair<String, Visit>, Calendar> data) {
        Calendar value = data.getValue().getValue().getCheckIn();
        return new ReadOnlyObjectWrapper<>(value);
    }
}

