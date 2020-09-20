package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserSystem;

public class ChangePasswordController {
    private StringBuffer oldPass;
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

    public void setOldPass(StringBuffer oldPass) {
        this.oldPass = oldPass;
    }

    @FXML
    public void onChangeAction() {
        boolean isValid = true;

        if (!oldPass.toString().equals(oldPassTextField.getText())) {
            isValid = false;
            invalidOldPass.setVisible(true);
        }

        if (!UserSystem.isValidPassword(newPassTextField.getText())) {
            isValid = false;
            invalidNewPass.setVisible(true);
        }

        if (isValid) {
            oldPass.setLength(0);
            oldPass.append(newPassTextField.getText());
            onExitAction();
        }
    }

    @FXML
    public void onExitAction() {
        ((Stage) newPassTextField.getScene().getWindow()).close();
    }
}
