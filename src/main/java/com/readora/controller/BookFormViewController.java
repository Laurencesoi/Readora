package com.readora.controller;

import com.readora.model.Book;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class BookFormViewController {

    @FXML
    private TextField bookIdField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> filterCategoryComboBox;

    @FXML
    private ComboBox<String> filterStatusComboBox;

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> bookIdColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> categoryColumn;

    @FXML
    private TableColumn<Book, String> statusColumn;

    private final ObservableList<Book> masterBookList = FXCollections.observableArrayList();
    private FilteredList<Book> filteredBookList;

    @FXML
    public void initialize() {
        categoryComboBox.setItems(FXCollections.observableArrayList(
                "Fiction", "Non-Fiction", "Science", "History", "Technology", "Education"
        ));

        statusComboBox.setItems(FXCollections.observableArrayList(
                "Available", "Borrowed", "Reserved"
        ));

        filterCategoryComboBox.setItems(FXCollections.observableArrayList(
                "All", "Fiction", "Non-Fiction", "Science", "History", "Technology", "Education"
        ));

        filterStatusComboBox.setItems(FXCollections.observableArrayList(
                "All", "Available", "Borrowed", "Reserved"
        ));

        filterCategoryComboBox.setValue("All");
        filterStatusComboBox.setValue("All");

        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        filteredBookList = new FilteredList<>(masterBookList, book -> true);
        bookTable.setItems(filteredBookList);

        bookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectedBook) -> {
            if (selectedBook != null) {
                bookIdField.setText(selectedBook.getBookId());
                titleField.setText(selectedBook.getTitle());
                authorField.setText(selectedBook.getAuthor());
                categoryComboBox.setValue(selectedBook.getCategory());
                statusComboBox.setValue(selectedBook.getStatus());
            }
        });
    }

    @FXML
    private void handleAddBook() {
        if (isInputInvalid()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please complete all book fields.");
            return;
        }

        Book book = new Book(
                bookIdField.getText().trim(),
                titleField.getText().trim(),
                authorField.getText().trim(),
                categoryComboBox.getValue(),
                statusComboBox.getValue()
        );

        masterBookList.add(book);
        handleClearFields();
        applyFilters();
    }

    @FXML
    private void handleUpdateBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to update.");
            return;
        }

        if (isInputInvalid()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please complete all book fields.");
            return;
        }

        selectedBook.setBookId(bookIdField.getText().trim());
        selectedBook.setTitle(titleField.getText().trim());
        selectedBook.setAuthor(authorField.getText().trim());
        selectedBook.setCategory(categoryComboBox.getValue());
        selectedBook.setStatus(statusComboBox.getValue());

        bookTable.refresh();
        handleClearFields();
        applyFilters();
    }

    @FXML
    private void handleDeleteBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to delete.");
            return;
        }

        masterBookList.remove(selectedBook);
        handleClearFields();
        applyFilters();
    }

    @FXML
    private void handleClearFields() {
        bookIdField.clear();
        titleField.clear();
        authorField.clear();
        categoryComboBox.setValue(null);
        statusComboBox.setValue(null);
        bookTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleSearch() {
        applyFilters();
    }

    @FXML
    private void handleFilter() {
        applyFilters();
    }

    private void applyFilters() {
        String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase().trim();
        String selectedCategory = filterCategoryComboBox.getValue();
        String selectedStatus = filterStatusComboBox.getValue();

        filteredBookList.setPredicate(book -> {
            boolean matchesSearch = searchText.isEmpty()
                    || book.getBookId().toLowerCase().contains(searchText)
                    || book.getTitle().toLowerCase().contains(searchText)
                    || book.getAuthor().toLowerCase().contains(searchText);

            boolean matchesCategory = selectedCategory == null
                    || selectedCategory.equals("All")
                    || book.getCategory().equals(selectedCategory);

            boolean matchesStatus = selectedStatus == null
                    || selectedStatus.equals("All")
                    || book.getStatus().equals(selectedStatus);

            return matchesSearch && matchesCategory && matchesStatus;
        });
    }

    private boolean isInputInvalid() {
        return bookIdField.getText().trim().isEmpty()
                || titleField.getText().trim().isEmpty()
                || authorField.getText().trim().isEmpty()
                || categoryComboBox.getValue() == null
                || statusComboBox.getValue() == null;
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        switchScene(event, "/view/MainView.fxml", "Readora Dashboard");
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
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to open the requested page.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}