package view;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.UserSystem;

public class ChangePasswordView extends Dialog<String> {
    private String oldPass;
    private final TextField oldPassTextField;
    private final TextField newPassTextField;
    private final Label oldPassLabel;
    private final Label newPassLabel;
    private final Label invalidOldPass;
    private final Label invalidNewPass;

    public ChangePasswordView(String oldPass) {
        super();
        getDialogPane().getStylesheets().add(getClass().getResource("/resources/Master.css").toExternalForm());
        setTitle("Change Password");
        this.oldPass = oldPass;
        oldPassTextField = new TextField();
        newPassTextField = new TextField();
        oldPassLabel = new Label("Old Password: ");
        newPassLabel = new Label("New Password: ");
        invalidOldPass = new Label("Old password does not match");
        invalidNewPass = new Label("Invalid new password");

        init();
    }

    public void init() {
        GridPane gridPane = new GridPane();

        gridPane.add(oldPassLabel, 0, 0);
        gridPane.add(oldPassTextField, 1, 0);
        gridPane.add(invalidOldPass, 1, 1);
        gridPane.add(newPassLabel, 0, 2);
        gridPane.add(newPassTextField, 1, 2);
        gridPane.add(invalidNewPass, 1, 3);

        oldPassLabel.setTranslateY(2);
        oldPassLabel.setFont(new Font(13));
        newPassLabel.setTranslateY(3);
        newPassLabel.setFont(new Font(13));

        invalidOldPass.getStyleClass().add("invalid-message");
        invalidOldPass.setVisible(false);
        invalidNewPass.getStyleClass().add("invalid-message");
        invalidNewPass.setVisible(false);
        getDialogPane().setContent(gridPane);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        final Button confirmButton = (Button) getDialogPane().lookupButton(ButtonType.OK);

        confirmButton.addEventFilter(ActionEvent.ACTION, e -> {
            boolean isValid = true;

            if (!oldPass.equals(oldPassTextField.getText())) {
                isValid = false;
                invalidOldPass.setVisible(true);
            }

            if (!UserSystem.isValidPassword(newPassTextField.getText())) {
                isValid = false;
                invalidNewPass.setVisible(true);
            }

            if (!isValid) {
                e.consume();
            }
        });

        oldPassTextField.setOnKeyPressed(e -> invalidOldPass.setVisible(false));
        newPassTextField.setOnKeyPressed(e -> invalidNewPass.setVisible(false));
        setResultConverter(dialogButton -> (dialogButton == ButtonType.OK)? newPassTextField.getText() : null);
    }
}
