package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.stage.Stage;
import model.Case;
import model.Citizen;
import model.GovOfficial;
import model.UserSystem;

import java.text.SimpleDateFormat;

public class CaseInformationController {
    /** This is the case being displayed. */
    private Case positive;
    /** This is the government official logged in. */
    private GovOfficial user;

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
     * Sets the user logged in and the case to display.
     * @param user the user logged in.
     * @param positive the specific case to view.
     */
    public void setModel(GovOfficial user, Case positive) {
        this.user = user;
        this.positive = positive;
        init();
    }

    /**
     * Handles the event where the government official assigns the case
     * to a contact tracer.
     * @param e the event where the "Assign" button was pressed.
     */
    public void onAssignAction(ActionEvent e) {
        if (positive.getTracer().equals("000")) {
            user.assignCase(positive, tracerBox.getValue());
            onBackAction(e);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Assign Case");
            alert.setHeaderText("Assign Error");
            alert.setContentText("Case already assigned!");
            alert.showAndWait();
        }
    }

    /**
     * Closes the current custom dialog box and brings the user back to the
     * main program.
     * @param e the event where the "Back" button was pressed.
     */
    public void onBackAction(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
