package com.readora.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {

    @FXML
    private Button adminMenuButton;

    private ContextMenu adminContextMenu;

    @FXML
    public void initialize() {
        adminContextMenu = new ContextMenu();

        MenuItem viewProfileItem = new MenuItem("View Profile");
        MenuItem settingsItem = new MenuItem("Settings");
        MenuItem helpItem = new MenuItem("Help");
        MenuItem logoutItem = new MenuItem("Logout");

        viewProfileItem.setOnAction(event -> handleViewProfile());
        settingsItem.setOnAction(event -> handleSettings());
        helpItem.setOnAction(event -> handleHelp());
        logoutItem.setOnAction(this::handleLogout);

        adminContextMenu.getItems().addAll(
                viewProfileItem,
                settingsItem,
                helpItem,
                logoutItem
        );
    }

    @FXML
    private void handleAdminMenu() {
        if (adminContextMenu.isShowing()) {
            adminContextMenu.hide();
        } else {
            double x = adminMenuButton.localToScreen(0, 0).getX();
            double y = adminMenuButton.localToScreen(0, adminMenuButton.getHeight()).getY();
            adminContextMenu.show(adminMenuButton, x, y);
        }
    }

    @FXML
    private void handleOpenBooks(ActionEvent event) {
        switchScene(event, "/view/BookFormView.fxml", "Readora - Book Management");
    }

    @FXML
    private void handleViewProfile() {
        showInfo("Profile", "Admin profile details can be added here.");
    }

    @FXML
    private void handleSettings() {
        showInfo("Settings", "System settings feature will be added soon.");
    }

    @FXML
    private void handleHelp() {
        showInfo("Help", "Readora Help Center is not yet available.");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) adminMenuButton.getScene().getWindow();
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();
            boolean wasMaximized = stage.isMaximized();

            Scene newScene = new Scene(root, currentWidth, currentHeight);

            stage.setTitle("Readora - Login");
            stage.setScene(newScene);
            stage.setMinWidth(1200);
            stage.setMinHeight(700);

            Platform.runLater(() -> {
                stage.setMaximized(wasMaximized || true);
                stage.centerOnScreen();
                stage.show();
            });

        } catch (IOException e) {
            e.printStackTrace();
            showInfo("Error", "Unable to return to this login page.");
        }
    }

    private void switchScene(ActionEvent event, String fxmlPath, String title) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            boolean wasMaximized = stage.isMaximized();
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene newScene = new Scene(root, currentWidth, currentHeight);

            stage.setMinWidth(1200);
            stage.setMinHeight(700);
            stage.setTitle(title);
            stage.setScene(newScene);

            Platform.runLater(() -> {
                stage.setMaximized(wasMaximized || true);
                stage.centerOnScreen();
                stage.show();
            });

        } catch (IOException e) {
            e.printStackTrace();
            showInfo("Error", "Unable to open the requested page.");
        }
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}