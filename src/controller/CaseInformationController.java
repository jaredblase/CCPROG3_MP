package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import model.Case;
import model.Citizen;
import model.GovOfficial;
import model.UserSystem;

import java.text.SimpleDateFormat;

public class CaseInformationController extends DialogController{
    /** This is the case being displayed. */
    private Case positive;

    @FXML
    private Label fullName;
    @FXML
    private Label homeAddress;
    @FXML
    private Label officeAddress;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label email;
    @FXML
    private Label caseNumber;
    @FXML
    private Label username;
    @FXML
    private Label reportDate;
    @FXML
    private Label tracer;
    @FXML
    private Label status;
    @FXML
    private ComboBox<String> tracerBox;
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

        tracerBox.setOnAction(e -> {
            if (tracerBox.getItems().contains("No tracers in the system")) {
                assignButton.setDisable(true);
            } else {
                assignButton.setDisable(tracerBox.getValue() == null || tracerBox.getValue().isEmpty());
            }
        });
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
        if (positive.getTracer().equals("000")) {
            ((GovOfficial) user).assignCase(positive, tracerBox.getValue());
            onCancelAction(e);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Assign Case");
            alert.setHeaderText("Assign Error");
            alert.setContentText("Case already assigned!");
            alert.showAndWait();
        }
    }
}
