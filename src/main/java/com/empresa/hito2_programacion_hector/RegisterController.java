package com.empresa.hito2_programacion_hector;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegisterController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button backButtonRegister;
    @FXML
    private TextField passwordField;

    @FXML
    private Button registerButtonRegister;

    private DatabaseManager dbManager;

    public void initialize() {
        dbManager = new DatabaseManager();
        backButtonRegister.setOnAction(event -> switchToInicioView());
        registerButtonRegister.setOnAction(event -> registerUser());
    }
    private void switchToInicioView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/inicio-view.fxml")));
            Stage stage = (Stage) backButtonRegister.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void registerUser() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        dbManager.registerUser(username, email, password);
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
    }
}
