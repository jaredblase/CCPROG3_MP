package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Citizen;
import model.UserSystem;

import view.ChangePasswordView;

import java.util.Optional;

public class ProfileController extends Controller {
    /** This is the user logged in */
    private Citizen user;

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
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private Button actionButton;
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
    private Label invalidPhoneNumber;
    @FXML
    private Label invalidEmail;
    @FXML
    private Label feedback;

    @FXML
    private MenuController menuController;
    private boolean isEditing;

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
        user = mainController.getUserModel();
        menuController.setMainController(super.mainController);
        firstName.setText(user.getName().getFirst());
        middleName.setText(user.getName().getMiddle());
        lastName.setText(user.getName().getLast());
        homeAddress.setText(user.getHomeAddress());
        officeAddress.setText(user.getOfficeAddress());
        phoneNumber.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        isEditing = false;
    }

    @FXML
    public void onEditAction() {
        isEditing = !isEditing;

        if (!isEditing) {
            Citizen temp = UserSystem.getUser(user.getUsername());
            boolean isValid = true;
            assert temp != null;
            /* check if there are changes in personal information and if there are changes,
            check if they are valid */
            if (!temp.getName().getFirst().equals(firstName.getText())) {
                if (!user.getName().setName(1, firstName.getText())) {
                    invalidFirstName.setVisible(true);
                    isValid = false;
                } else if (!user.getIsChanged()) {
                    user.setIsChanged(true);
                }
            }
            if (!temp.getName().getMiddle().equals(middleName.getText())) {
                if (!user.getName().setName(2, middleName.getText())) {
                    invalidMiddleName.setVisible(true);
                    isValid = false;
                } else if (!user.getIsChanged()) {
                    user.setIsChanged(true);
                }
            }
            if (!temp.getName().getLast().equals(lastName.getText())) {
                if (!user.getName().setName(3, lastName.getText())) {
                    invalidLastName.setVisible(true);
                    isValid = false;
                } else if (!user.getIsChanged()) {
                    user.setIsChanged(true);
                }
            }
            if (!temp.getHomeAddress().equals(homeAddress.getText())) {
                if (!user.setPersonalDetails(1, homeAddress.getText())) {
                    invalidHomeAddress.setVisible(true);
                    isValid = false;
                } else if (!user.getIsChanged()) {
                    user.setIsChanged(true);
                }
            }
            if (!temp.getOfficeAddress().equals(officeAddress.getText())) {
                if (!user.setPersonalDetails(2, homeAddress.getText())) {
                    invalidOfficeAddress.setVisible(true);
                    isValid = false;
                } else if (!user.getIsChanged()) {
                    user.setIsChanged(true);
                }
            }
            if (!temp.getPhoneNumber().equals(phoneNumber.getText())) {
                if (!user.setPersonalDetails(3, phoneNumber.getText())) {
                    invalidPhoneNumber.setVisible(true);
                    isValid = false;
                } else if (!user.getIsChanged()) {
                    user.setIsChanged(true);
                }
            }
            if (!temp.getEmail().equals(email.getText())) {
                if (!user.setPersonalDetails(4, email.getText())) {
                    invalidEmail.setVisible(true);
                    isValid = false;
                } else if (!user.getIsChanged()) {
                    user.setIsChanged(true);
                }
            }

            if (isValid) {
                update();   // to also update changes in the menuController (Update display name at the right side)
                actionButton.setText("Edit");
                feedback.setVisible(true);
            } else {
                isEditing = !isEditing;
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
    }

    @FXML
    public void changePasswordAction() {
        feedback.setVisible(false);
        ChangePasswordView dialog = new ChangePasswordView(user.getPassword());

        Optional<String> result = dialog.showAndWait();
        result.ifPresentOrElse(e -> {
            try {
                user.setPassword(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }, () -> feedback.setVisible(true));
    }
}
