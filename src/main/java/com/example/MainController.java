package com.example;

import com.example.core.YGuardEngine;
import com.example.util.JarInspector;
import com.example.util.JarInspector.ClassInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

public class MainController {

    @FXML private TextField inputJarPath;
    @FXML private TextField outputPath;
    @FXML private TreeView<String> packageTree;
    @FXML private TextArea logConsole;
    @FXML private ListView<String> externalLibsList;
    @FXML private Button runButton;

    @FXML private CheckBox attrLines;
    @FXML private CheckBox attrSource;
    @FXML private CheckBox attrVars;
    @FXML private CheckBox attrDeprecated;

    @FXML private CheckBox replaceClassStrings;
    @FXML private ComboBox<String> namingScheme;

    @FXML private ComboBox<String> languageSelector;

    @FXML private ResourceBundle resources;

    private File selectedJar;
    private final List<String> externalLibs = new ArrayList<>();

    @FXML
    public void initialize() {
        packageTree.setCellFactory(CheckBoxTreeCell.forTreeView());
        
        // Sync language selector with current locale
        String currentLang = resources.getLocale().getLanguage();
        if (currentLang.equals("en")) {
            languageSelector.setValue("English");
        } else {
            languageSelector.setValue("Türkçe");
        }
    }

    @FXML
    private void handleLanguageChange() {
        String selected = languageSelector.getValue();
        if ("English".equals(selected)) {
            MainApp.setLanguage("en");
        } else {
            MainApp.setLanguage("tr");
        }
    }

    @FXML
    private void handleSelectJar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));
        selectedJar = fileChooser.showOpenDialog(new Stage());

        if (selectedJar != null) {
            inputJarPath.setText(selectedJar.getAbsolutePath());
            outputPath.setText(selectedJar.getParent());
            loadPackageTree(selectedJar.getAbsolutePath());
            runButton.setDisable(false);
        }
    }

    @FXML
    private void handleSelectOutputDir() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        File dir = dirChooser.showDialog(new Stage());
        if (dir != null) {
            outputPath.setText(dir.getAbsolutePath());
        }
    }

    @FXML
    private void handleAddLibrary() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));
        List<File> libs = fileChooser.showOpenMultipleDialog(new Stage());
        if (libs != null) {
            for (File lib : libs) {
                externalLibs.add(lib.getAbsolutePath());
                externalLibsList.getItems().add(lib.getName());
            }
        }
    }

    private void loadPackageTree(String jarPath) {
        log(resources.getString("log.scanning"));
        new Thread(() -> {
            try {
                Map<String, List<ClassInfo>> structure = JarInspector.inspectJar(jarPath);
                Platform.runLater(() -> {
                    CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>(resources.getString("ui.select_packages"));
                    root.setExpanded(true);

                    for (Map.Entry<String, List<ClassInfo>> entry : structure.entrySet()) {
                        CheckBoxTreeItem<String> pkgItem = new CheckBoxTreeItem<>("pkg:" + entry.getKey());
                        for (ClassInfo ci : entry.getValue()) {
                            CheckBoxTreeItem<String> classItem = new CheckBoxTreeItem<>("class:" + ci.name);
                            for (String m : ci.methods) {
                                classItem.getChildren().add(new CheckBoxTreeItem<>("method:" + ci.name + "#" + m));
                            }
                            for (String f : ci.fields) {
                                classItem.getChildren().add(new CheckBoxTreeItem<>("field:" + ci.name + "#" + f));
                            }
                            pkgItem.getChildren().add(classItem);
                        }
                        root.getChildren().add(pkgItem);
                    }
                    packageTree.setRoot(root);
                    log(MessageFormat.format(resources.getString("log.scanned"), structure.size()));
                });
            } catch (Exception e) {
                Platform.runLater(() -> log(MessageFormat.format(resources.getString("log.error"), e.getMessage())));
            }
        }).start();
    }

    @FXML
    private void handleRunObfuscation() {
        if (selectedJar == null) return;

        List<String> keepRules = new ArrayList<>();
        collectKeepRules(packageTree.getRoot(), keepRules);

        List<String> attributes = new ArrayList<>();
        if (attrLines.isSelected()) attributes.add("LineNumberTable");
        if (attrSource.isSelected()) attributes.add("SourceFile");
        if (attrVars.isSelected()) attributes.add("LocalVariableTable");
        if (attrDeprecated.isSelected()) attributes.add("Deprecated");

        boolean replaceStrings = replaceClassStrings.isSelected();
        String scheme = namingScheme.getValue();
        String outJar = new File(outputPath.getText(), selectedJar.getName().replace(".jar", "-obf.jar")).getAbsolutePath();
        
        log(resources.getString("log.started"));
        runButton.setDisable(true);

        new Thread(() -> {
            try {
                YGuardEngine.run(selectedJar.getAbsolutePath(), outJar, keepRules, attributes, externalLibs, replaceStrings, scheme);
                Platform.runLater(() -> {
                    log(MessageFormat.format(resources.getString("log.success"), outJar));
                    runButton.setDisable(false);
                    new Alert(Alert.AlertType.INFORMATION, resources.getString("ui.obfuscate_now")).showAndWait();
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    log(MessageFormat.format(resources.getString("log.error"), e.getMessage()));
                    runButton.setDisable(false);
                });
            }
        }).start();
    }

    private void collectKeepRules(TreeItem<String> item, List<String> rules) {
        if (item instanceof CheckBoxTreeItem) {
            CheckBoxTreeItem<String> cbItem = (CheckBoxTreeItem<String>) item;
            if (cbItem.isSelected()) {
                rules.add(cbItem.getValue());
            } else if (cbItem.isIndeterminate()) {
                for (TreeItem<String> child : cbItem.getChildren()) {
                    collectKeepRules(child, rules);
                }
            }
        }
    }

    private void log(String msg) {
        Platform.runLater(() -> logConsole.appendText("[" + new java.util.Date().toString() + "] " + msg + "\n"));
    }
}
