package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import model.Citizen;
import model.Name;
import model.UserSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * The ModifyRoleController class handles the creation of a new account and the
 * modification of the role of an existing user in the system.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see Controller
 */
public class ModifyRoleController extends Controller {
    /** The ChoiceBox that contains the role options of account creation or role modification. */
    @FXML
    private ChoiceBox<String> choiceBox;
    /** The username of the new user to be created or the user whose role is to be modified. */
    @FXML
    private TextField username;
    /** The message that is displayed if the username is invalid. */
    @FXML
    private Label invalidUsername;
    /** The Button that, when pressed, creates a new account or modifies the specified user. */
    @FXML
    private Button actionButton;
    /** The message that is displayed after an action is executed successfully. */
    @FXML
    private Label modifyFeedback;
    /** The MenuController that handles the menu of the user logged in. */
    @FXML
    private MenuController menuController;

    /**
     * Sets up the main controller to the menu controller.
     */
    @Override
    protected void update() {
        menuController.setMainController(mainController);
    }

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader. Sets
     * up the behaviour of the text field, choice box, invalid message label, and button.
     */
    @FXML
    public void initialize() {
        username.setOnKeyPressed(e -> {
            invalidUsername.setVisible(false);
            actionButton.setDisable(true);
            choiceBox.getItems().clear();
        });

        username.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {
                actionButton.setDisable(true);
            } else {
                int index = UserSystem.getIndexOf(username.getText());
                // username is that of a user in the system that is not the user logged in
                if (index != -1 && !username.getText().equalsIgnoreCase(mainController.getUserModel().getUsername())) {
                    choiceBox.getItems().clear();
                    for (Role r : Role.values()) {
                        choiceBox.getItems().add(r.getDisplay());
                    }
                    choiceBox.getItems().remove(Role.getOrdinalOf(UserSystem.getRoleOf(index)));
                    actionButton.setText("Modify");
                // username not yet taken and valid
                } else if (UserSystem.isValidNewUsername(username.getText())) {
                    choiceBox.getItems().clear();
                    for (Role r : Role.values()) {
                        choiceBox.getItems().add(r.getDisplay());
                    }
                    choiceBox.getItems().remove(Role.getOrdinalOf("citizen"));
                    actionButton.setText("Create");
                } else {
                    invalidUsername.setVisible(true);
                }
            }
        });

        choiceBox.setOnAction(e -> {
            if (choiceBox.getValue() != null && !choiceBox.getValue().isEmpty()) {
                actionButton.setDisable(false);
            }
        });
    }

    /**
     * Handles the event where the action button is pressed.
     */
    @FXML
    public void onAction() {
        if (UserSystem.isValidNewUsername(username.getText())) { // create new user
            UserSystem.register(new Citizen(new Name("", "", ""), "", "", "", "",
                    username.getText().toUpperCase(), "", true));
            UserSystem.setRoleOf(UserSystem.getIndexOf(username.getText()), Role.getValueOf(choiceBox.getValue()));

            mainController.changeScene(MainController.REGISTER_2_VIEW);
        } else { // modify user
            UserSystem.setRoleOf(UserSystem.getIndexOf(username.getText()), Role.getValueOf(choiceBox.getValue()));
            modifyFeedback.setText(username.getText() + "'s role has been successfully modified");
            modifyFeedback.setVisible(true);

            username.clear();
            choiceBox.getItems().clear();
            actionButton.setDisable(true);
        }
    }
}

enum Role {
    CITIZEN("citizen", "Citizen"),
    TRACER ("tracer", "Contact Tracer"),
    OFFICIAL("official", "Government Official");


    private final String value;
    private final String display;
    private static final Map<String, Role> valueLookup = new HashMap<>();
    private static final Map<String, Role> displayLookup = new HashMap<>();

    static {
        for (Role r : Role.values()) {
            valueLookup.put(r.getValue(), r);
            displayLookup.put(r.getDisplay(), r);
        }
    }

    Role(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public String getValue() {
        return value;
    }

    public String getDisplay() {
        return display;
    }

    public static String getValueOf(String display) {
        return displayLookup.get(display).value;
    }

    public static int getOrdinalOf(String value) {
        return valueLookup.get(value).ordinal();
    }
}
