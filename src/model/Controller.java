package model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    public void handleLoginButtonAction() {
        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());

//        Stage stage = (Stage) loginButton.getScene().getWindow();
//        View view = new View();
//        view.changeScene(stage, "login.fxml");
    }

    public void handleRegisterButtonAction() {
        System.out.println("Creating new account...");
    }
}