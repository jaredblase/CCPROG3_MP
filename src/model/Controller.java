package model;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    public void handleLoginButtonAction() {
        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());
    }

    public void handleRegisterButtonAction() {
        System.out.println("Creating new account...");
    }
}