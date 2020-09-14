package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.GovOfficial;
import model.Tracer;
import view.CheckInView;
import view.ReportView;

import java.util.Calendar;
import java.util.Optional;

public class MenuController extends Controller {
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
        if (mainController.getUserModel() instanceof GovOfficial) {
            govActions.setVisible(true);
        } else if (mainController.getUserModel() instanceof Tracer) {
            tracerActions.setVisible(true);
        }

        username.setText(mainController.getUserModel().getName().getFullName());
    }

    @FXML
    public void goToProfileAction() {
        mainController.changeScene(MainController.PROFILE_VIEW);
    }

    @FXML
    public void reportPositiveAction() {
        ReportView dialog = new ReportView();

        Optional<Calendar> result = dialog.showAndWait();
        result.ifPresent(e -> System.out.println(e.getTime()));
    }

    @FXML
    public void checkInAction() {
        CheckInView dialog = new CheckInView();

        Optional<Pair<String, Calendar>> result = dialog.showAndWait();
        result.ifPresent(e -> System.out.println(e.getKey() + "\n" + e.getValue().getTime()));
    }

    @FXML
    public void showCaseInformationAction() {
        mainController.changeScene(MainController.CASE_INFO_VIEW);
    }

    @FXML
    public void handleLogoutAction() {
        mainController.changeScene(MainController.LOGIN_VIEW);
    }
}
