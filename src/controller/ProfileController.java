package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Citizen;

public class ProfileController extends Controller {
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
    private MenuController menuController;
    private boolean isEditing;

    @FXML
    public void update() {
        Citizen user = mainController.getUserModel();

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

        if (isEditing) {
            actionButton.setText("Update Profile");
        } else {
            actionButton.setText("Edit");
        }

        firstName.setDisable(!isEditing);
        middleName.setDisable(!isEditing);
        lastName.setDisable(!isEditing);
        homeAddress.setDisable(!isEditing);
        officeAddress.setDisable(!isEditing);
        phoneNumber.setDisable(!isEditing);
        email.setDisable(!isEditing);
    }
}
