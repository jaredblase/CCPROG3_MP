package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Citizen;
import model.Name;
import model.UserSystem;

public class RegistrationController extends Controller {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label invalidUsername;
    @FXML
    private Label invalidPassword;

    public RegistrationController() {

    }

    public void initialize() {
        usernameTextField.setOnKeyPressed(e -> invalidUsername.setVisible(false));
        passwordTextField.setOnKeyPressed(e -> invalidPassword.setVisible(false));
    }

    @Override
    public void update() {

    }

    @FXML
    public void handleBackToLoginAction() {
        mainController.changeScene(MainController.LOGIN_VIEW);
    }

    @FXML
    public void handleGoFillDetailsAction() {
        boolean isValid = true;

        if (!UserSystem.isValidNewUsername(usernameTextField.getText())) {
            invalidUsername.setVisible(true);
            isValid = false;
        }

        if (!UserSystem.isValidPassword(passwordTextField.getText())) {
            invalidPassword.setVisible(true);
            isValid = false;
        }

        if (isValid) {
            UserSystem.register(new Citizen(new Name("", "", ""), "", "", "", "",
                            usernameTextField.getText().toUpperCase(), passwordTextField.getText()));
            mainController.changeScene(MainController.REGISTER_2_VIEW);
        }
    }
}
