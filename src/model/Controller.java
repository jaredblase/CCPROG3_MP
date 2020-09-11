package model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label username;
    @FXML
    private Label invalidLoginMessage;

    private Citizen user;

    @FXML
    public void handleLoginButtonAction(ActionEvent e) {
        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());
        user = UserSystem.login(usernameTextField.getText(), passwordTextField.getText());
        if (user == null) {
            invalidLoginMessage.setVisible(true);
            usernameTextField.setText("");
            passwordTextField.setText("");
        } else {
            new View().changeScene((Node) e.getSource(), "Main Menu.fxml");
            username.setText("Gabriel Pua");
        }
    }

    @FXML
    public void handleCreateNewAccountAction(ActionEvent e) {
        new View().changeScene((Node) e.getSource(), "Registration Form Part 1.fxml");
    }

    @FXML
    public void handleExitButtonAction(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleBackToLoginAction(MouseEvent e) {
        new View().changeScene((Node) e.getSource(), "Login Screen.fxml");
    }

    @FXML
    public void handleGoFillDetailsAction(ActionEvent e) {
        new View().changeScene((Node) e.getSource(), "Registration Form Part 2.fxml");
    }

    @FXML
    public void handleRegisterButtonAction(ActionEvent e) {
        // show a popup here maybe?
        new View().changeScene((Node) e.getSource(), "Login Screen.fxml");
    }
}