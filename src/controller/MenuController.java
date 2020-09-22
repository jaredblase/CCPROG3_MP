package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Citizen;
import model.GovOfficial;
import model.Tracer;
import model.UserSystem;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class MenuController extends Controller {
    @FXML
    private Label fullName;
    @FXML
    private Label username;
    @FXML
    private VBox govActions;
    @FXML
    private VBox tracerActions;
    @FXML
    private VBox inContact;
    @FXML
    private Label estCode;
    @FXML
    private Label date;

    @Override
    public void update() {
        Citizen user = mainController.getUserModel();
        if (user instanceof GovOfficial) {
            govActions.setVisible(true);
        } else if (user instanceof Tracer) {
            tracerActions.setVisible(true);
        }

        fullName.setText(user.getName().getFullName());
        username.setText(user.getUsername());

        // prompt
        if (!user.getIsPositive() && user.getContactPlace() != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM,dd,yyyy");
            inContact.setVisible(true);
            estCode.setText(user.getContactPlace().getEstCode());
            date.setText(format.format(user.getContactPlace().getCheckIn().getTime()));
        }
    }

    @FXML
    public void goToProfileAction() {
        mainController.changeScene(MainController.PROFILE_VIEW);
    }

    @FXML
    public void reportPositiveAction() {
        Citizen user = mainController.getUserModel();
        if (!user.getIsPositive()) {
            Stage dialog = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Report Positive.fxml"));

            try {
                dialog.setScene(new Scene(loader.load()));
                dialog.setTitle("Report Positive");
                dialog.initModality(Modality.APPLICATION_MODAL);
                ((ReportPositiveController) loader.getController()).setUser(user);
                dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Report Positive");
            alert.setHeaderText("Report Error");
            alert.setContentText("You have already reported positive!");
            alert.showAndWait();
        }
    }

    @FXML
    public void checkInAction() {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Check In.fxml"));

        try {
            dialog.setScene(new Scene(loader.load()));
            dialog.setTitle("Check In");
            dialog.initModality(Modality.APPLICATION_MODAL);
            ((DialogController) loader.getController()).setUser(mainController.getUserModel());
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showCaseInformationAction() {
        mainController.changeScene(MainController.CASE_TABLE_VIEW);
    }

    @FXML
    public void showModifyRoleAction() {
        mainController.changeScene(MainController.MODIFY_ROLE_VIEW);
    }

    @FXML
    public void showTraceAction() {
        mainController.changeScene(MainController.TRACE_VIEW);
    }

    @FXML
    public void handleLogoutAction() {
        UserSystem.updateUser(mainController.getUserModel());
        mainController.changeScene(MainController.LOGIN_VIEW);
    }
}
