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

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the behaviour of the text boxes and invalid message labels.
     */
    @FXML
    public void initialize() {
        usernameTextField.setOnKeyPressed(e -> invalidUsername.setVisible(false));
        passwordTextField.setOnKeyPressed(e -> invalidPassword.setVisible(false));
    }

    @Override
    public void update() {

    }

    /**
     * Handles the even where the back arrow image is pressed.
     * Brings the user back to the login page.
     */
    @FXML
    public void handleBackToLoginAction() {
        mainController.changeScene(MainController.LOGIN_VIEW);
    }

    /**
     * Validates the user's input and, when valid, adds the created account to the system.
     */
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
                            usernameTextField.getText().toUpperCase(), passwordTextField.getText(), true));
            mainController.changeScene(MainController.REGISTER_2_VIEW);
        }
    }
}
