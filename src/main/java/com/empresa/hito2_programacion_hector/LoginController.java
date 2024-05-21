package com.empresa.hito2_programacion_hector;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButtonInicio;

    private DatabaseManagerApp dbManager; // Cambiado a DatabaseManagerApp

    public LoginController() {
        dbManager = new DatabaseManagerApp(); // Cambiado a DatabaseManagerApp
    }

    public void initialize() {
        registerButtonInicio.setOnAction(event -> switchToRegisterView());
        loginButton.setOnAction(event -> verifyCredentials());
    }

    private void switchToRegisterView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/register-view.fxml")));
            Stage stage = (Stage) registerButtonInicio.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void verifyCredentials() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isValid = dbManager.verifyCredentials(username, password);

        usernameField.clear();
        passwordField.clear();

        if (isValid) {
            // Handle successful login
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/app-view.fxml")));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Diálogo de Error");
                alert.setHeaderText("Fallo en la Navegación");
                alert.setContentText("¡No se puede navegar a la página principal!");
                alert.showAndWait();
            }
        } else {
            // Handle failed login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Diálogo de Error");
            alert.setHeaderText("Fallo en el Inicio de Sesión");
            alert.setContentText("¡Nombre de usuario o contraseña inválidos!");
            alert.showAndWait();
        }
    }
}