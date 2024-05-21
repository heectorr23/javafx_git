package com.empresa.hito2_programacion_hector;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    private Button loginButton;

    private DatabaseManager dbManager;

    public void initialize() {
        dbManager = new DatabaseManager();
        loginButton.setOnAction(event -> switchToLoginView());
    }


    private void switchToLoginView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/login-view.fxml")));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}