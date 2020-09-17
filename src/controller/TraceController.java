package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;

import javafx.scene.control.cell.PropertyValueFactory;
import model.Citizen;
import model.Tracer;
import model.UserSystem;

import java.util.ArrayList;

public class TraceController extends Controller {
    /** This is the contact tracer logged in. */
    private Tracer tracer;

    @FXML
    private ComboBox<Integer> caseNumber;
    @FXML
    private TableColumn<Citizen, String> usernameCol;
    @FXML
    private MenuController menuController;

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
    public void initialize() {

    }

    @FXML
    public void handleInformCitizenAction() {
        ArrayList<String> contacts = tracer.trace(caseNumber.getValue());
        ArrayList<Citizen> citizens = new ArrayList<>();

        for (String i: contacts) {
            citizens.add(UserSystem.getUser(i));
        }

        // remove case number from choices
        caseNumber.getItems().removeAll(caseNumber.getValue());
    }
}
