package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Citizen;

public abstract class DialogController {
    protected Citizen user;
    @FXML
    private Button cancelButton;

    public void setUser(Citizen user) {
        this.user = user;
    }

    public abstract void onOKAction(Event e);

    public void onCancelAction(Event e) {
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
    }
}
