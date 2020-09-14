package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Registration2Controller extends Controller {
    @FXML
    private TextField firstName;
    @FXML
    private TextField middleName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField homeAddress;
    @FXML
    private TextField officeAddress;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Label invalidFirstName;
    @FXML
    private Label invalidMiddleName;
    @FXML
    private Label invalidLastName;
    @FXML
    private Label invalidHomeAddress;
    @FXML
    private Label invalidOfficeAddress;
    @FXML
    private Label invalidEmail;
    @FXML
    private Label invalidPhoneNumber;

    @Override
    public void update() {

    }

    @FXML
    public void handleRegisterButtonAction() {
        // test
        if (!firstName.getText().isBlank()) {
            mainController.changeScene(MainController.LOGIN_VIEW);
        } else {
            invalidFirstName.setVisible(true);
        }
    }
}
