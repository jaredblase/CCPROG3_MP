package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Citizen;
import model.UserSystem;

public class LoginController extends Controller {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label invalidLoginMessage;

    public LoginController() {

    }

    @Override
    public void update() {

    }

    @FXML
    public void handleLoginButtonAction(ActionEvent e) {
        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());
        Citizen citizen = UserSystem.login(usernameTextField.getText(), passwordTextField.getText());
        if (citizen == null) {
            invalidLoginMessage.setVisible(true);
            usernameTextField.clear();
            passwordTextField.clear();
        } else {
            mainController.setUserModel(citizen);
            mainController.changeScene(MainController.MAIN_MENU_VIEW);
        }
    }

    @FXML
    public void handleCreateNewAccountAction(ActionEvent e) {
        mainController.changeScene(MainController.REGISTER_VIEW);
    }

    @FXML
    public void handleExitButtonAction(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
