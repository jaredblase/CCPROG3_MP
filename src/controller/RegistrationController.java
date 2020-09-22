package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import model.Citizen;
import model.Name;
import model.UserSystem;

/**
 * The RegistrationController class handles the input of username and password for a new
 * user to be added to the system.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see Controller
 */
public class RegistrationController extends Controller {
    /** The username input. */
    @FXML
    private TextField usernameTextField;
    /** The password input. */
    @FXML
    private TextField passwordTextField;
    /** The message displayed if the username is invalid. */
    @FXML
    private Label invalidUsername;
    /** The message displayed if the password is invalid. */
    @FXML
    private Label invalidPassword;

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the behaviour of the text fields and invalid message labels.
     */
    @FXML
    public void initialize() {
        usernameTextField.setOnKeyPressed(e -> invalidUsername.setVisible(false));
        passwordTextField.setOnKeyPressed(e -> invalidPassword.setVisible(false));
    }

    @Override
    protected void update() {

    }

    /**
     * Handles the event where the back arrow image is pressed.
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

        // checks if username and password are valid
        if (!UserSystem.isValidNewUsername(usernameTextField.getText())) {
            invalidUsername.setVisible(true);
            isValid = false;
        }

        if (!UserSystem.isValidPassword(passwordTextField.getText())) {
            invalidPassword.setVisible(true);
            isValid = false;
        }

        if (isValid) { // both are valid
            UserSystem.register(new Citizen(new Name("", "", ""), "", "", "", "",
                            usernameTextField.getText().toUpperCase(), passwordTextField.getText(), true));
            mainController.changeScene(MainController.REGISTER_2_VIEW);
        }
    }
}
