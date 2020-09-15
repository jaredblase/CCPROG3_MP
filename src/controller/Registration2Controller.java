package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Citizen;
import model.UserSystem;

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

    public void initialize() {
        firstName.setOnKeyPressed(e -> invalidFirstName.setVisible(false));
        middleName.setOnKeyPressed(e -> invalidMiddleName.setVisible(false));
        lastName.setOnKeyPressed(e -> invalidLastName.setVisible(false));
        homeAddress.setOnKeyPressed(e -> invalidHomeAddress.setVisible(false));
        officeAddress.setOnKeyPressed(e -> invalidOfficeAddress.setVisible(false));
        phoneNumber.setOnKeyPressed(e -> invalidPhoneNumber.setVisible(false));
        email.setOnKeyPressed(e -> invalidEmail.setVisible(false));
    }

    @Override
    public void update() {

    }

    @FXML
    public void handleBackToForm1Action() {
        UserSystem.removeUser();
        mainController.changeScene(MainController.REGISTER_VIEW);
    }

    @FXML
    public void handleRegisterButtonAction() {
        boolean isValid = true;
        Citizen citizen = UserSystem.getUser(UserSystem.getUsername(UserSystem.getNumUsers() - 1));

        assert citizen != null;
        if (!citizen.getName().setName(1, firstName.getText())) {
            invalidFirstName.setVisible(true);
            isValid = false;
        }

        if (!citizen.getName().setName(2, middleName.getText())) {
            invalidMiddleName.setVisible(true);
            isValid = false;
        }

        if (!citizen.getName().setName(3, lastName.getText())) {
            invalidLastName.setVisible(true);
            isValid = false;
        }

        if (!citizen.setPersonalDetails(1, homeAddress.getText())){
            invalidHomeAddress.setVisible(true);
            isValid = false;
        }

        if (!citizen.setPersonalDetails(2, officeAddress.getText())) {
            invalidOfficeAddress.setVisible(true);
            isValid = false;
        }

        if (!citizen.setPersonalDetails(3, phoneNumber.getText())) {
            invalidPhoneNumber.setVisible(true);
            isValid = false;
        }

        if (!citizen.setPersonalDetails(4, email.getText())) {
            invalidEmail.setVisible(true);
            isValid = false;
        }

        if (isValid) {
            mainController.changeScene(MainController.LOGIN_VIEW);
        }
    }
}
