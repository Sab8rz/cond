package com.example.javafxhomework;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    private MainApplication mainApplication;

    public void setMainApplication(MainApplication mainApplication) {this.mainApplication = mainApplication;}

    @FXML private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String correctUsername = MainApplication.getLogin();
        String correctPassword = MainApplication.getPassword();

        if (username.equals(correctUsername) && password.equals(correctPassword)) {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();

            try {
                mainApplication.openMainScene(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка входа");
            alert.setHeaderText(null);
            alert.setContentText("Неверный логин или пароль!");
            alert.showAndWait();
        }
    }
}