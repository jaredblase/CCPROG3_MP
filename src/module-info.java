module CCPROG.MP {
    requires javafx.fxml;
    requires javafx.controls;

    opens controller;
    opens driver;
    opens model;
    opens view;
}