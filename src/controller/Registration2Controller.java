package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import model.Citizen;
import model.GovOfficial;
import model.UserSystem;

/**
 * The Registration2Controller class handles the input of personal information for a new
 * user to be added to the system.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see Controller
 */
public class Registration2Controller extends Controller {
    /** Indicator whether the previous scene is Registration Form Part 1. */
    private boolean fromRegister;

    /** The first name of the user to be registered. */
    @FXML
    private TextField firstName;
    /** The middle name of the user to be registered. */
    @FXML
    private TextField middleName;
    /** The last name of the user to be registered. */
    @FXML
    private TextField lastName;
    /** The home address of the user to be registered. */
    @FXML
    private TextField homeAddress;
    /** The office address of the user to be registered. */
    @FXML
    private TextField officeAddress;
    /** The phone number of the user to be registered. */
    @FXML
    private TextField phoneNumber;
    /** The email of the user to be registered. */
    @FXML
    private TextField email;
    /** The message displayed if the first name is invalid. */
    @FXML
    private Label invalidFirstName;
    /** The message displayed if the middle name is invalid. */
    @FXML
    private Label invalidMiddleName;
    /** The message displayed if the last name is invalid. */
    @FXML
    private Label invalidLastName;
    /** The message displayed if the home address is invalid. */
    @FXML
    private Label invalidHomeAddress;
    /** The message displayed if the office address is invalid. */
    @FXML
    private Label invalidOfficeAddress;
    /** The message displayed if the phone number is invalid. */
    @FXML
    private Label invalidPhoneNumber;
    /** The message displayed if the email is invalid. */
    @FXML
    private Label invalidEmail;

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the behaviour of the text fields and invalid message labels.
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

    /**
     * Determines whether the previous scene is Registration Form Part 1.
     */
    @Override
    protected void update() {
        fromRegister = !UserSystem.getLastUser().getPassword().equals("");
    }

    /**
     * Handles the event where the user presses the back button.
     * Brings the user back to the previous scene.
     */
    @FXML
    public void handleBackAction() {
        UserSystem.removeLastUser();
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
        // check if the information inputs are valid
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

        if (isValid) { // all of the information is valid
            if (fromRegister) {
                mainController.changeScene(MainController.LOGIN_VIEW);
            } else {
                // generate random password
                citizen.setPassword(((GovOfficial) mainController.getUserModel()).generatePassword());
                mainController.changeScene(MainController.MODIFY_ROLE_VIEW);

                // display the random password in a dialog box
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Create Account");
                alert.setHeaderText("Account Password");
                alert.setContentText("Password of newly created account is " + citizen.getPassword());
                alert.showAndWait();
            }
        }
    }
}
