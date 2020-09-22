package controller;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import model.Citizen;

/**
 * This abstract class is extended by Controllers that handles a
 * specific action by the user logged in.
 */
public abstract class DialogController {
    /** This is the user logged in. */
    protected Citizen user;

    /**
     * Sets the user logged in.
     * @param user the user logged in.
     */
    public void setUser(Citizen user) {
        this.user = user;
    }

    /**
     * Handles the event where the user does a certain action.
     * @param e the event where the user confirms his action.
     */
    public abstract void onOKAction(Event e);

    /**
     * Displays a dialog box to indicate that the user's action is successful.
     * @param message the message to be displayed in the dialog box.
     */
    public void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText("Successful!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the event where the user cancels his action.
     * @param e the event where the user cancels his action.
     */
    public void onCancelAction(Event e) {
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
    }
}
