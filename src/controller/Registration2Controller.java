package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Registration2Controller {
    private MainController mainController;

    public Registration2Controller() {

    }

    public Registration2Controller(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void handleRegisterButtonAction(ActionEvent e) {
        // show a popup here maybe?
        mainController.changeScene(MainController.REGISTER_2_VIEW);
    }
}
