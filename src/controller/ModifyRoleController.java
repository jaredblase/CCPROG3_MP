package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.UserSystem;

import java.util.HashMap;
import java.util.Map;

public class ModifyRoleController extends Controller {
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField username;
    @FXML
    private Label invalidUsername;
    @FXML
    private Button modifyButton;
    @FXML
    private Label modifyFeedback;
    @FXML
    private MenuController menuController;

    @Override
    public void update() {
        menuController.setMainController(mainController);
    }

    public void initialize() {
        username.setOnKeyPressed(e -> {
            invalidUsername.setVisible(false);
            modifyButton.setDisable(true);
        });

        username.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {
                modifyButton.setDisable(true);
            } else {
                int index = UserSystem.getIndexOf(username.getText());
                if (index == -1 ||  username.getText().equalsIgnoreCase(mainController.getUserModel().getUsername())) {
                    invalidUsername.setVisible(true);
                } else {
                    choiceBox.getItems().clear();
                    for (Role r : Role.values()) {
                        choiceBox.getItems().add(r.getDisplay());
                    }
                    choiceBox.getItems().remove(Role.getOrdinalOf(UserSystem.getRoleOf(index)));
                }
            }
        });

        choiceBox.setOnAction(e -> {
            if (choiceBox.getValue() != null && !choiceBox.getValue().isEmpty()) {
                modifyButton.setDisable(false);
            }
        });
    }

    @FXML
    public void onModifyAction() {
        UserSystem.setRoleOf(UserSystem.getIndexOf(username.getText()), Role.getValueOf(choiceBox.getValue()));
        modifyFeedback.setText(username.getText() + "'s role has been successfully modified");
        modifyFeedback.setVisible(true);

        username.clear();
        choiceBox.getItems().clear();
        modifyButton.setDisable(true);
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
