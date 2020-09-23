package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import model.*;

import java.text.SimpleDateFormat;

/**
 * The CaseInformationController holds the information of a positive case and sets
 * the labels to be displayed to the information of both the positive user and the
 * positive case. This class also handles the assignment of a positive case to a
 * contact tracer.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see DialogController
 */
public class CaseInformationController extends DialogController{
    /** This is the case being displayed. */
    private Case positive;

    /** The full name of the positive user. */
    @FXML
    private Label fullName;
    /** The home address of the positive user. */
    @FXML
    private Label homeAddress;
    /** The office address of the positive user. */
    @FXML
    private Label officeAddress;
    /** The phone number of the positive user. */
    @FXML
    private Label phoneNumber;
    /** The email of the positive user. */
    @FXML
    private Label email;
    /** The case number of the positive case. */
    @FXML
    private Label caseNumber;
    /** The username of the positive user. */
    @FXML
    private Label username;
    /** The date the positive case was reported. */
    @FXML
    private Label reportDate;
    /** The username of the contact tracer assigned to the positive case. */
    @FXML
    private Label tracer;
    /** The status of the positive case. */
    @FXML
    private Label status;
    /** The Label for the ComboBox below. */
    @FXML
    private Label tracerLabel;
    /** The ComboBox that holds the list of usernames of contact tracers in the system. */
    @FXML
    private ComboBox<String> tracerBox;
    /** The Button that, when clicked, assigns the positive case to a selected contact tracer.*/
    @FXML
    private Button assignButton;

    /**
     * Automatically called when the corresponding fxml file is loaded by FXML loader.
     * Sets up the ComboBox and the Assign button behaviour.
     */
    @FXML
    public void initialize() {
        tracerBox.getItems().addAll(UserSystem.getTracers());
        if (tracerBox.getItems().size() == 0) {
            tracerBox.getItems().add("No tracers in the system");
        }

        tracerBox.setOnAction(e -> assignButton.setDisable(tracerBox.getValue() == null || tracerBox.getValue().isEmpty()
                || tracerBox.getItems().contains("No tracers in the system")));
    }

    /**
     * Updates the labels in the view based on the case information received.
     */
    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Citizen temp = UserSystem.getUser(positive.getUsername());
        assert temp != null;

        fullName.setText(temp.getName().getFullName());
        homeAddress.setText(temp.getHomeAddress());
        officeAddress.setText(temp.getOfficeAddress());
        phoneNumber.setText(temp.getPhoneNumber());
        email.setText(temp.getEmail());
        caseNumber.setText(String.valueOf(positive.getCaseNum()));
        username.setText(positive.getUsername());
        reportDate.setText(format.format(positive.getReportDate().getTime()));
        tracer.setText(positive.getTracer());
        status.setText(String.valueOf(positive.getStatus()));

        if (!positive.getTracer().equals("000")) {
            tracerBox.setVisible(false);
            assignButton.setVisible(false);
            tracerLabel.setVisible(false);
        }
    }

    /**
     * Sets the case to display.
     * @param positive the specific case to view.
     */
    public void setModel(Case positive) {
        this.positive = positive;
        init();
    }

    /**
     * Handles the event where the government official assigns the case
     * to a contact tracer.
     * @param e the event where the "Assign" button was pressed.
     */
    public void onOKAction(Event e) {
        ((GovOfficial) user).assignCase(positive, tracerBox.getValue());
        onCancelAction(e);
    }
}
