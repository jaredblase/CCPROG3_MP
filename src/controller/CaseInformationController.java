package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CaseInformationController extends Controller {
    @FXML
    private MenuController menuController;
    @FXML
    private TextField cityFilter;
    @FXML
    private DatePicker startFilter;
    @FXML
    private DatePicker endFilter;

    @Override
    public void update() {
        menuController.setMainController(mainController);
    }

    @FXML
    public void handleFilterAction() {

    }
}
