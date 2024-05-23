package com.empresa.hito2_programacion_hector;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField captchaField;
    @FXML
    private ImageView captchaImageView;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButtonInicio;

    private DatabaseManager dbManager;
    private CaptchaGenerator captchaGenerator;
    private String captchaText;

    public LoginController() {
        dbManager = new DatabaseManager();
        captchaGenerator = new CaptchaGenerator();
    }

    public void initialize() {
        registerButtonInicio.setOnAction(event -> switchToRegisterView());
        loginButton.setOnAction(event -> handleLogin());
        generateCaptcha();
    }

    private void switchToRegisterView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/register-view.fxml")));
            Stage stage = (Stage) registerButtonInicio.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateCaptcha() {
        captchaText = captchaGenerator.getProducer().createText();
        BufferedImage captchaImage = captchaGenerator.getProducer().createImage(captchaText);

        // Convert the CAPTCHA image to a JavaFX Image object
        Image image = SwingFXUtils.toFXImage(captchaImage, null);

        // Display the image in the ImageView
        captchaImageView.setImage(image);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String userInputCaptcha = captchaField.getText();

        boolean isValidCaptcha = dbManager.verifyCaptcha(userInputCaptcha, captchaText);
        boolean isValidCredentials = dbManager.verifyCredentials(username, password);

        usernameField.clear();
        passwordField.clear();
        captchaField.clear();

        if (isValidCaptcha && isValidCredentials) {
            // Handle successful login
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/empresa/hito2_programacion_hector/app-view.fxml")));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle failed login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("Las credenciales proporcionadas son incorrectas. Por favor, inténtalo de nuevo.");
            alert.showAndWait();
        }

        // Generate a new CAPTCHA for the next login attempt
        generateCaptcha();
    }
}