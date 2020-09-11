package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class RegistrationController extends Controller {
    private MainController mainController;

    public RegistrationController() {

    }

    @Override
    public void update() {

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
