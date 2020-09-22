package controller;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Citizen;

public abstract class DialogController {
    /** This is the user logged in. */
    protected Citizen user;

    public void setUser(Citizen user) {
        this.user = user;
    }

    public abstract void onOKAction(Event e);

    public void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText("Successful!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onCancelAction(Event e) {
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
    }
}
