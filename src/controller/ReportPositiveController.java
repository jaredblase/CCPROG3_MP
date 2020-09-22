package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * The ReportPositiveController handles the action of the user logged in to report positive.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see DialogController
 */
public class ReportPositiveController extends DialogController {
    /** The date the user reported positive. */
    @FXML
    private DatePicker datePicker;
    /** The message displayed if the date is invalid. */
    @FXML
    private Label invalidDate;

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the behaviour of the date picker and invalid message labels.
     */
    @FXML
    public void initialize() {
        datePicker.setOnAction(e -> invalidDate.setVisible(false));
    }

    /**
     * Handles the event where the user confirms that he is positive.
     * @param e the event where the "Confirm" button was pressed.
     */
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
