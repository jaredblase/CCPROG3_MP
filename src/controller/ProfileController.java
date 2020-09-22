package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Citizen;
import model.UserSystem;

import java.io.IOException;

/**
 * The ProfileController class sets the text fields to the information of the user logged in.
 * This class also handles the changing of personal information of the user.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see Controller
 */
public class ProfileController extends Controller {
    /** Indicates whether the user is currently editing his profile or not */
    private boolean isEditing;

    /** The first name of the user logged in. */
    @FXML
    private TextField firstName;
    /** The middle name of the user logged in. */
    @FXML
    private TextField middleName;
    /** The last name of the user logged in. */
    @FXML
    private TextField lastName;
    /** The home address of the user logged in. */
    @FXML
    private TextField homeAddress;
    /** The office address of the user logged in. */
    @FXML
    private TextField officeAddress;
    /** The phone number of the user logged in. */
    @FXML
    private TextField phoneNumber;
    /** The email of the user logged in. */
    @FXML
    private TextField email;
    /** The Button that, when pressed, enables editing or saving of
     * the personal information of the user logged in. */
    @FXML
    private Button actionButton;
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
    /** The Button that, when pressed, handles the changing of password of the user logged in. */
    @FXML
    private Button changePassButton;
    /** The message displayed if the changing of personal information is successful. */
    @FXML
    private Label feedback;
    /** The MenuController that handles the menu of the user logged in. */
    @FXML
    private MenuController menuController;

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
     * Sets up the information to display from the user logged in.
     */
    @Override
    protected void update() {
        Citizen user = mainController.getUserModel();
        menuController.setMainController(super.mainController);

        firstName.setText(user.getName().getFirst());
        middleName.setText(user.getName().getMiddle());
        lastName.setText(user.getName().getLast());
        homeAddress.setText(user.getHomeAddress());
        officeAddress.setText(user.getOfficeAddress());
        phoneNumber.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
    }

    /**
     * Depending on the value of isEditing, this method will either setup the
     * text boxes to become editable or it saves the edited information.
     */
    @FXML
    public void onEditAction() {
        isEditing = !isEditing;

        if (!isEditing) {
            Citizen user = mainController.getUserModel();
            Citizen temp = UserSystem.getUser(user.getUsername());
            boolean isValid = true, isChangedNow = false;

            /* check if there are changes in personal information and if there are changes,
            check if they are valid */
            assert temp != null;
            if (!temp.getName().getFirst().equals(firstName.getText())) {
                if (!user.getName().setName(1, firstName.getText())) {
                    invalidFirstName.setVisible(true);
                    isValid = false;
                } else {
                    user.setIsChanged(true);
                    isChangedNow = true;
                }
            }
            if (!temp.getName().getMiddle().equals(middleName.getText())) {
                if (!user.getName().setName(2, middleName.getText())) {
                    invalidMiddleName.setVisible(true);
                    isValid = false;
                } else {
                    user.setIsChanged(true);
                    isChangedNow = true;
                }
            }
            if (!temp.getName().getLast().equals(lastName.getText())) {
                if (!user.getName().setName(3, lastName.getText())) {
                    invalidLastName.setVisible(true);
                    isValid = false;
                } else {
                    user.setIsChanged(true);
                    isChangedNow = true;
                }
            }
            if (!temp.getHomeAddress().equals(homeAddress.getText())) {
                if (!user.setPersonalDetails(1, homeAddress.getText())) {
                    invalidHomeAddress.setVisible(true);
                    isValid = false;
                } else {
                    user.setIsChanged(true);
                    isChangedNow = true;
                }
            }
            if (!temp.getOfficeAddress().equals(officeAddress.getText())) {
                if (!user.setPersonalDetails(2, officeAddress.getText())) {
                    invalidOfficeAddress.setVisible(true);
                    isValid = false;
                } else {
                    user.setIsChanged(true);
                    isChangedNow = true;
                }
            }
            if (!temp.getPhoneNumber().equals(phoneNumber.getText())) {
                if (!user.setPersonalDetails(3, phoneNumber.getText())) {
                    invalidPhoneNumber.setVisible(true);
                    isValid = false;
                } else {
                    user.setIsChanged(true);
                    isChangedNow = true;
                }
            }
            if (!temp.getEmail().equals(email.getText())) {
                if (!user.setPersonalDetails(4, email.getText())) {
                    invalidEmail.setVisible(true);
                    isValid = false;
                } else {
                    user.setIsChanged(true);
                    isChangedNow = true;
                }
            }

            if (isValid) {
                if (isChangedNow) {
                    update();   // to also update changes in the menuController (Update display name at the right side)
                    feedback.setVisible(true);
                }
                actionButton.setText("Edit");
                isEditing = false;
            } else {
                isEditing = true;
            }

        } else {
            feedback.setVisible(false);
            actionButton.setText("Update Profile");
        }

        firstName.setDisable(!isEditing);
        middleName.setDisable(!isEditing);
        lastName.setDisable(!isEditing);
        homeAddress.setDisable(!isEditing);
        officeAddress.setDisable(!isEditing);
        phoneNumber.setDisable(!isEditing);
        email.setDisable(!isEditing);
        changePassButton.setVisible(!isEditing);
    }

    /**
     * Display a dialog when the user wants to change his password.
     */
    @FXML
    public void changePasswordAction() {
        Citizen user = mainController.getUserModel();
        feedback.setVisible(false);
        String temp = user.getPassword();
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Change Password.fxml"));

        try {
            // set upper left corner icon
            dialog.getIcons().add(
                    new Image(getClass().getResource("/resources/lock.png").toString()));
            dialog.setScene(new Scene(loader.load()));
            dialog.setTitle("Change Password");
            dialog.initModality(Modality.APPLICATION_MODAL);
            ((DialogController) loader.getController()).setUser(user);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        feedback.setVisible(!temp.equals(user.getPassword()));
    }
}
