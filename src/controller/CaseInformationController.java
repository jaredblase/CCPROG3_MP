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
    private ComboBox<String> tracers;
    @FXML
    private Button assignButton;
    @FXML
    private Button backButton;

    public CaseInformationController() {

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
        tracers.getItems().addAll(UserSystem.getTracers());
    }

    public void setCase(Case positive) {
        this.positive = positive;
    }

    public void onAssignAction() {

    }

    public void onBackAction(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
