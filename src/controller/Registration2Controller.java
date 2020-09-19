package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Citizen;
import model.GovOfficial;
import model.UserSystem;

public class Registration2Controller extends Controller {
    /** Indicator whether the previous scene is Registration Form Part 1. */
    private boolean fromRegister;

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

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the behaviour of the invalid message labels.
     */
    @FXML
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
        fromRegister = !UserSystem.getLastUser().getPassword().equals("");
    }

    /**
     * Handles the event where the user presses the back button.
     * Brings the user back to the previous scene.
     */
    @FXML
    public void handleBackAction() {
        UserSystem.removeUser();
        if (fromRegister) {
            mainController.changeScene(MainController.REGISTER_VIEW);
        } else {
            mainController.changeScene(MainController.MODIFY_ROLE_VIEW);
        }
    }

    /**
     * Handles the event where the user presses the register button.
     * This will add his information to the user system.
     */
    @FXML
    public void handleRegisterButtonAction() {
        boolean isValid = true;
        Citizen citizen = UserSystem.getLastUser();

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
            if (fromRegister) {
                mainController.changeScene(MainController.LOGIN_VIEW);
            } else {
                citizen.setPassword(((GovOfficial) mainController.getUserModel()).generatePassword());
                mainController.changeScene(MainController.MODIFY_ROLE_VIEW);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Create Account");
                alert.setHeaderText("Account Password");
                alert.setContentText("Password of newly created account is " + citizen.getPassword());
                alert.showAndWait();
            }
        }
    }
}
