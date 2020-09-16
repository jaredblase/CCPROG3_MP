package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import model.Case;
import model.Tracer;
import model.Visit;

public class TraceController extends Controller {
    private Tracer tracer;
    @FXML
    private ComboBox<Integer> caseNumber;
    @FXML
    private TableColumn<Visit, String> visit;
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

        for(Case i : tracer.getAssigned())
            caseNums.add(i.getCaseNum());

        caseNumber.setItems(caseNums);
    }

    @FXML
    public void handleInformCitizenAction() {
        // remove case number from choices
        caseNumber.getItems().removeAll(caseNumber.getValue());
    }
}
