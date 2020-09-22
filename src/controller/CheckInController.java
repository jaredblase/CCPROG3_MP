package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.Calendar;

public class CheckInController extends DialogController {
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

    @Override
    @FXML
    public void onOKAction(Event e) {
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
            showConfirmation("Visit was logged.");
            onCancelAction(e);
        }
    }
}
