package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * The CheckInController handles the check in to an establishment of the user logged in.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see DialogController
 */
public class CheckInController extends DialogController {
    /** The establishment code of the user's visit. */
    @FXML
    private TextField estCodeTextField;
    /** The date of the user's visit to an establishment. */
    @FXML
    private DatePicker datePicker;
    /** The message displayed if the establishment code is invalid. */
    @FXML
    private Label invalidEstCode;
    /** The messaged displayed if the date is invalid. */
    @FXML
    private Label invalidDate;

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the behaviour of the text field, date picker, and invalid message labels.
     */
    @FXML
    public void initialize() {
        estCodeTextField.setOnKeyTyped(e -> invalidEstCode.setVisible(false));
        datePicker.setOnAction(e -> invalidDate.setVisible(false));
    }

    /**
     * Handles the event where the user confirms his visit to an establishment.
     * @param e the event where the "Confirm" button was pressed.
     */
    @Override
    @FXML
    public void onOKAction(Event e) {
        boolean isValid = true;

        if (estCodeTextField.getText().isEmpty()) {
            isValid = false;
            invalidEstCode.setText("Establishment code cannot be blank");
            invalidEstCode.setVisible(true);
        } else if (estCodeTextField.getText().contains(" ")) {
            isValid = false;
            invalidEstCode.setText("Space character not allowed");
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
