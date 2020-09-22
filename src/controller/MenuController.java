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

/**
 * The MenuController class sets the labels to the information of the user logged in. This
 * class also determines which actions are available to the user and handles the action selected
 * by the user.
 * @author Gabriel Pua
 * @author Jared Sy
 * @version 1.0
 * @see Controller
 */
public class MenuController extends Controller {
    /** The full name of the user logged in. */
    @FXML
    private Label fullName;
    /** The username of the user logged in. */
    @FXML
    private Label username;
    /** The VBox containing the actions available to a government official. */
    @FXML
    private VBox govActions;
    /** The VBox containing the actions available to a contact tracer. */
    @FXML
    private VBox tracerActions;
    /** The VBox containing the prompt message indicating possible contact. */
    @FXML
    private VBox inContact;
    /** The establishment code indicating where the user logged in may have been infected. */
    @FXML
    private Label estCode;
    /** The date when the user logged in may have been infected. */
    @FXML
    private Label date;

    /**
     * Sets up the actions available to the user logged in,the information of the user,
     * and the prompt message indicating possible contact.
     */
    @Override
    protected void update() {
        Citizen user = mainController.getUserModel();
        // determine available actions
        if (user instanceof GovOfficial) {
            govActions.setVisible(true);
        } else if (user instanceof Tracer) {
            tracerActions.setVisible(true);
        }

        fullName.setText(user.getName().getFullName());
        username.setText(user.getUsername());

        // prompt message indicating possible contact
        if (!user.getIsPositive() && user.getContactPlace() != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM,dd,yyyy");
            inContact.setVisible(true);
            estCode.setText(user.getContactPlace().getEstCode());
            date.setText(format.format(user.getContactPlace().getCheckIn().getTime()));
        }
    }

    /**
     * Handles the event where the "Profile" button is pressed.
     */
    @FXML
    public void goToProfileAction() {
        mainController.changeScene(MainController.PROFILE_VIEW);
    }

    /**
     * Handles the event where the "Report Positive" button is pressed.
     */
    @FXML
    public void reportPositiveAction() {
        Citizen user = mainController.getUserModel();
        if (!user.getIsPositive()) { // user is not positive
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
        } else { // user is already positive
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Report Positive");
            alert.setHeaderText("Report Error");
            alert.setContentText("You have already reported positive!");
            alert.showAndWait();
        }
    }

    /**
     * Handles the event where the "Check In" button is pressed.
     */
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

    /**
     * Handles the event where the "Case Information" button is pressed.
     */
    @FXML
    public void showCaseInformationAction() {
        mainController.changeScene(MainController.CASE_TABLE_VIEW);
    }

    /**
     * Handles the event where the "Create/Modify Roles" button is pressed.
     */
    @FXML
    public void showModifyRoleAction() {
        mainController.changeScene(MainController.MODIFY_ROLE_VIEW);
    }

    /**
     * Handles the event where the "Show Cases" button is pressed.
     */
    @FXML
    public void showTraceAction() {
        mainController.changeScene(MainController.TRACE_VIEW);
    }

    /**
     * Handles the event where the "Log Out" button is pressed.
     */
    @FXML
    public void handleLogoutAction() {
        UserSystem.updateUser(mainController.getUserModel());
        mainController.changeScene(MainController.LOGIN_VIEW);
    }
}
