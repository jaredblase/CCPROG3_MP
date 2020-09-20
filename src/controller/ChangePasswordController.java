package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChangePasswordController extends DialogController {
    @FXML
    private TextField oldPassTextField;
    @FXML
    private TextField newPassTextField;
    @FXML
    private Label invalidOldPass;
    @FXML
    private Label invalidNewPass;

    @FXML
    public void initialize() {
        oldPassTextField.setOnKeyTyped(e -> invalidOldPass.setVisible(false));
        newPassTextField.setOnKeyTyped(e -> invalidNewPass.setVisible(false));
    }

    @Override
    public void onOKAction(Event e) {
        if (user.getPassword().equals(oldPassTextField.getText())) {
            invalidOldPass.setVisible(true);
        } else if (user.setPassword(newPassTextField.getText())) {
            onCancelAction(e);
        } else {
            invalidNewPass.setVisible(true);
        }
    }
}
