package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.Citizen;
import model.GovOfficial;
import model.Tracer;
import model.UserSystem;
import view.CheckInView;
import view.ReportView;

import java.util.Calendar;
import java.util.Optional;

public class MenuController extends Controller {
    /** This is the user logged in */
    private Citizen user;

    @FXML
    private Label fullName;
    @FXML
    private Label username;
    @FXML
    private VBox govActions;
    @FXML
    private VBox tracerActions;

    public MenuController() {
    }

    @Override
    public void update() {
        user = mainController.getUserModel();
        if (user instanceof GovOfficial) {
            govActions.setVisible(true);
        } else if (user instanceof Tracer) {
            tracerActions.setVisible(true);
        }

        fullName.setText(user.getName().getFullName());
        username.setText(user.getUsername());
    }

    @FXML
    public void goToProfileAction() {
        mainController.changeScene(MainController.PROFILE_VIEW);
    }

    @FXML
    public void reportPositiveAction() {
        if (!user.getIsPositive()) {
            ReportView dialog = new ReportView();

            Optional<Calendar> result = dialog.showAndWait();
            if (result.isPresent()) {
                System.out.println(result.get().getTime());
                user.reportPositive(result.get());
            }
        } //else already positive dialog box?
    }

    @FXML
    public void checkInAction() {
        CheckInView dialog = new CheckInView();

        Optional<Pair<String, Calendar>> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println(result.get().getKey() + "\n" + result.get().getValue().getTime());
            user.checkIn(result.get().getKey(), result.get().getValue());
        }
    }

    @FXML
    public void showCaseInformationAction() {
        mainController.changeScene(MainController.CASE_INFO_VIEW);
    }

    @FXML
    public void showModifyRoleAction() {
        mainController.changeScene(MainController.MODIFY_ROLE_VIEW);
    }

    @FXML
    public void handleLogoutAction() {
        UserSystem.updateUser(user);
        mainController.changeScene(MainController.LOGIN_VIEW);
    }
}
