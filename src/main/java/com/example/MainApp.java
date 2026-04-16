package com.example;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {

    private static Stage primaryStage;
    private static Locale currentLocale = Locale.of("tr");

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        
        loadView();
    }

    public static void loadView() throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.messages", currentLocale);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/example/main-view.fxml"), bundle);
        Pane root = fxmlLoader.load();

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle(bundle.getString("app.title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setLanguage(String lang) {
        currentLocale = Locale.of(lang);
        try {
            loadView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
