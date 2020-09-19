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
    /** This is the contact tracer logged in. */
    private Tracer tracer;

    @FXML
    private ComboBox<Integer> caseNumber;
    @FXML
    private TableView<Pair<String, Visit>> tableView;
    @FXML
    private TableColumn<Pair<String, Visit>, String> usernameCol;
    @FXML
    private TableColumn<Pair<String, Visit>, String> estCodeCol;
    @FXML
    private TableColumn<Pair<String, Visit>, Calendar> dateCol;
    @FXML
    private MenuController menuController;

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

    @Override
    public void update() {
        menuController.setMainController(mainController);

        tracer = (Tracer) mainController.getUserModel();
        init();
    }

    private void init() {
        ObservableList<Integer> caseNums = FXCollections.observableArrayList();

        caseNums.addAll(tracer.getAssigned());

        caseNumber.setItems(caseNums);
    }

    @FXML
    public void onTraceAction() {
        ObservableList<Pair<String, Visit>> contacts = FXCollections.observableArrayList();
        if (caseNumber.getValue() != null) {
            tracer.trace(caseNumber.getValue(), contacts);
            tableView.setItems(contacts);
        }

        // remove case number from choices
        init();
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

