package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private Button editButton;
    @FXML
    private MenuController menuController;
    private boolean isEditing;

    @FXML
    public void update() {
        menuController.setMainController(super.mainController);
        isEditing = false;
    }

    @FXML
    public void onEditAction() {
        isEditing = !isEditing;

        if (isEditing) {
            editButton.setText("Update Profile");
        } else {
            editButton.setText("Edit");
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
