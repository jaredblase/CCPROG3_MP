package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class RegistrationController {
    private MainController mainController;

    public RegistrationController() {

    }

    public RegistrationController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void handleBackToLoginAction(MouseEvent e) {
        mainController.changeScene(MainController.LOGIN_VIEW);
    }

    @FXML
    public void handleGoFillDetailsAction(ActionEvent e) {
//        if (usernameTextField)
        mainController.changeScene(MainController.REGISTER_VIEW);
    }
}
