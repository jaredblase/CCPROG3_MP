package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * The ChangePasswordController handles the changing of password of the user logged in.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see DialogController
 */
public class ChangePasswordController extends DialogController {
    /** The old password of the user. */
    @FXML
    private TextField oldPassTextField;
    /** The new password of the user. */
    @FXML
    private TextField newPassTextField;
    /** The message displayed if the old password is incorrect. */
    @FXML
    private Label invalidOldPass;
    /** The message displayed if the new password is invalid. */
    @FXML
    private Label invalidNewPass;

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     *  Sets up the behaviour of the text fields and invalid message labels.
     */
    @FXML
    public void initialize() {
        oldPassTextField.setOnKeyTyped(e -> invalidOldPass.setVisible(false));
        newPassTextField.setOnKeyTyped(e -> invalidNewPass.setVisible(false));
    }

    /**
     * Handles the event where the user confirms the changing of his password.
     * @param e the event where the "Confirm" button was pressed.
     */
    @Override
    @FXML
    public void onOKAction(Event e) {
        if (user.getPassword().equals(oldPassTextField.getText()) &&
                !user.getPassword().equals(newPassTextField.getText()) &&
                user.setPassword(newPassTextField.getText())) {
            showConfirmation("Password was changed.");
            onCancelAction(e);
        } else {
            invalidNewPass.setText("Invalid new password");
            invalidNewPass.setVisible(true);
        }

        if (!user.getPassword().equals(oldPassTextField.getText())) {
            invalidOldPass.setVisible(true);
        }

        if (user.getPassword().equals(newPassTextField.getText())) {
            invalidNewPass.setText("New password matches old password");
            invalidNewPass.setVisible(true);
        }
    }
}
