package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.Citizen;
import model.UserSystem;

/**
 * The LoginController handles the logging in of a user.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see Controller
 */
public class LoginController extends Controller {
    /** The username input. */
    @FXML
    private TextField usernameTextField;
    /** The password input. */
    @FXML
    private TextField passwordTextField;
    /** The message displayed if the login is unsuccessful. */
    @FXML
    private Label invalidLoginMessage;

    @Override
    protected void update() {

    }

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the behaviour of the text fields and invalid message labels.
     */
    @FXML
    public void initialize() {
        // when a key is typed in these fields, the invalid message is hidden
        usernameTextField.setOnKeyPressed(e -> invalidLoginMessage.setVisible(false));
        passwordTextField.setOnKeyPressed(e -> invalidLoginMessage.setVisible(false));
    }

    /**
     * Handles the action event when the login button is pressed.
     */
    @FXML
    public void handleLoginButtonAction() {
        Citizen citizen = UserSystem.login(usernameTextField.getText(), passwordTextField.getText());
        if (citizen == null) {
            invalidLoginMessage.setVisible(true);
            passwordTextField.clear();
        } else {
            mainController.setUserModel(citizen);
            mainController.changeScene(MainController.PROFILE_VIEW);
        }
    }

    /**
     * Handles the event where the create new account link is pressed.
     */
    @FXML
    public void handleCreateNewAccountAction() {
        mainController.changeScene(MainController.REGISTER_VIEW);
    }

    /**
     * Handles the event where the 'X' (exit) button is pressed.
     * @param e the action event.
     */
    @FXML
    public void handleExitButtonAction(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();

        UserSystem.exitSystem();
    }
}
