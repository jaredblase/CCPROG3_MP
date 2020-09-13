package controller;

import javafx.fxml.FXML;

public class Registration2Controller extends Controller {
    public Registration2Controller() {

    }

    @Override
    public void update() {

    }

    @FXML
    public void handleRegisterButtonAction() {
        // show a popup here maybe?
        mainController.changeScene(MainController.LOGIN_VIEW);
    }
}
