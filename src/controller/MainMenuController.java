package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainMenuController extends Controller {
    @FXML
    private Label username;

    public MainMenuController() {
    }

    @Override
    public void update() {
        username.setText(mainController.getUserModel().getName().getFullName());
    }
}
