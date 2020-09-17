package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.stage.Stage;
import model.Case;
import model.Citizen;
import model.UserSystem;

import java.text.SimpleDateFormat;

public class CaseInformationController{
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

    public void initialize() {
        tracerBox.setOnAction(e -> {
            if (tracerBox.getItems().contains("No tracers in the system"))
                assignButton.setDisable(true);
            else
                assignButton.setDisable(tracerBox.getValue() == null || tracerBox.getValue().isEmpty());
        });
    }

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
        tracerBox.getItems().addAll(UserSystem.getTracers());
        tracerBox.getItems().add("No tracers in the system");
    }

    public void setCase(Case positive) {
        this.positive = positive;
    }

    public void onAssignAction(ActionEvent e) {
        if (positive.getTracer().equals("000")) {
            positive.setTracer(tracerBox.getValue());
            onBackAction(e);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Assign Case");
            alert.setHeaderText("Assign Error");
            alert.setContentText("Case already assigned!");
            alert.showAndWait();
        }
    }

    public void onBackAction(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
