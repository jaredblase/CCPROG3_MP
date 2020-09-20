package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Citizen;

import java.time.LocalDate;
import java.util.Calendar;

public class CheckInController {
    private Citizen user;
    @FXML
    private TextField estCodeTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label invalidEstCode;
    @FXML
    private Label invalidDate;

    @FXML
    public void initialize() {
        estCodeTextField.setOnKeyTyped(e -> invalidEstCode.setVisible(false));
        datePicker.setOnAction(e -> invalidDate.setVisible(false));
    }

    public void setUser(Citizen user) {
        this.user = user;
    }

    @FXML
    public void onCheckInAction() {
        boolean isValid = true;

        if (estCodeTextField.getText().isEmpty()) {
            isValid = false;
            invalidEstCode.setVisible(true);
        }

        if (datePicker.getValue() == null) {
            isValid = false;
            invalidDate.setVisible(true);
        }

        if (isValid) {
            LocalDate ld = datePicker.getValue();
            Calendar date = Calendar.getInstance();
            date.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
            user.checkIn(estCodeTextField.getText(), date);
            onExitAction();
        }
    }

    @FXML
    public void onExitAction() {
        ((Stage) datePicker.getScene().getWindow()).close();
    }
}
