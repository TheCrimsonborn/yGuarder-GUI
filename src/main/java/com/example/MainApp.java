package com.example;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    private static Stage primaryStage;
    private static Locale currentLocale = Locale.of("tr");

    @Override
    public void start(Stage stage) throws Exception {
        initStage(stage);
        loadView();
    }

    // Static setter — avoids instance method writing to static field (S2696)
    private static void initStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);
        setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
    }

    public static void loadView() throws IOException {
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
            log.error("Failed to reload view for language '{}': {}", lang, e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
