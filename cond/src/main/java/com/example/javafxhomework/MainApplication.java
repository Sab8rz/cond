package com.example.javafxhomework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.Parent;
import java.io.IOException;

public class MainApplication extends Application {
    private static final String LOGIN = "admin";
    private static final String PASSWORD = "password";

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent loginRoot = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginController.setMainApplication(this);

        Scene loginScene = new Scene(loginRoot);
        primaryStage.setTitle("Вход в систему");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public void openMainScene(Stage primaryStage) throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight() * 0.98;

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Parent mainRoot = mainLoader.load();

        Scene mainScene = new Scene(mainRoot, width, height);
        primaryStage.setTitle("Кондитерская");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {launch(args);}

    public static String getLogin() {return LOGIN;}

    public static String getPassword() {return PASSWORD;}
}
