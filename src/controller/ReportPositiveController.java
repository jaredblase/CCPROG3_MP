package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.Calendar;

public class ReportPositiveController extends DialogController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label invalidDate;

    @FXML
    public void initialize() {
        datePicker.setOnAction(e -> invalidDate.setVisible(false));
    }

    @Override
    public void onOKAction(Event e) {
        if (datePicker.getValue() != null) {
            LocalDate ld = datePicker.getValue();
            Calendar date = Calendar.getInstance();
            date.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
            user.reportPositive(date);
            showConfirmation("Case has been recorded. Thank you for reporting.");
            onCancelAction(e);
        } else {
            invalidDate.setVisible(true);
        }
    }
}
