package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Registration2Controller extends Controller {
    private MainController mainController;

    public Registration2Controller() {

    }

    @Override
    public void update() {

    }

    @FXML
    public void handleRegisterButtonAction(ActionEvent e) {
        // show a popup here maybe?
        mainController.changeScene(MainController.REGISTER_2_VIEW);
    }
}
