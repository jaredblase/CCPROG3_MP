package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainMenuController {
    @FXML
    private Label username;
    private MainController mainController;

    public MainMenuController() {

    }

    public MainMenuController(MainController mainController) {
        this.mainController = mainController;
//        initialize();
    }

    public void initialize() {
        username.setText(mainController.getUserModel().getName().getFullName());
    }
}
