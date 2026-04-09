package com.readora.controller;

import com.readora.model.Book;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BookFormViewController {

    @FXML
    private Button adminMenuButton;

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

    @FXML
    private TableColumn<Book, String> actionColumn;

    @FXML
    private HBox popupOverlay;

    @FXML
    private Label popupTitleLabel;

    private final ObservableList<Book> masterBookList = FXCollections.observableArrayList();
    private FilteredList<Book> filteredBookList;
    private ContextMenu adminContextMenu;
    private Book editingBook;

    @FXML
    public void initialize() {
        setupAdminMenu();
        setupComboBoxes();
        setupTable();
        loadDummyBooks();
        applyFilters();
        hidePopup();
    }

    private void setupAdminMenu() {
        adminContextMenu = new ContextMenu();

        MenuItem viewProfileItem = new MenuItem("View Profile");
        MenuItem settingsItem = new MenuItem("Settings");
        MenuItem helpItem = new MenuItem("Help");
        MenuItem logoutItem = new MenuItem("Logout");

        viewProfileItem.setOnAction(event -> showAlert(Alert.AlertType.INFORMATION, "Profile", "Admin profile details can be added here."));
        settingsItem.setOnAction(event -> showAlert(Alert.AlertType.INFORMATION, "Settings", "System settings feature will be added soon."));
        helpItem.setOnAction(event -> showAlert(Alert.AlertType.INFORMATION, "Help", "Readora Help Center is not yet available."));
        logoutItem.setOnAction(this::handleLogout);

        adminContextMenu.getItems().addAll(viewProfileItem, settingsItem, helpItem, logoutItem);
    }

    private void setupComboBoxes() {
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
    }

    private void setupTable() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        filteredBookList = new FilteredList<>(masterBookList, book -> true);
        bookTable.setItems(filteredBookList);

        actionColumn.setCellValueFactory(cellData -> new SimpleStringProperty("Actions"));
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionBox = new HBox(8, editButton, deleteButton);

            {
                actionBox.setAlignment(Pos.CENTER);
                editButton.getStyleClass().add("table-edit-button");
                deleteButton.getStyleClass().add("table-delete-button");

                editButton.setOnAction(event -> {
                    Book selectedBook = getTableView().getItems().get(getIndex());
                    openEditPopup(selectedBook);
                });

                deleteButton.setOnAction(event -> {
                    Book selectedBook = getTableView().getItems().get(getIndex());
                    masterBookList.remove(selectedBook);
                    applyFilters();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
                setContentDisplay(empty ? ContentDisplay.TEXT_ONLY : ContentDisplay.GRAPHIC_ONLY);
            }
        });
    }

    private void loadDummyBooks() {
        masterBookList.addAll(
                new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Available"),
                new Book("B002", "A Brief History of Time", "Stephen Hawking", "Science", "Borrowed"),
                new Book("B003", "Clean Code", "Robert C. Martin", "Technology", "Available"),
                new Book("B004", "Sapiens", "Yuval Noah Harari", "History", "Reserved"),
                new Book("B005", "The Montessori Method", "Maria Montessori", "Education", "Available")
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
    private void handleGoDashboard(ActionEvent event) {
        switchScene(event, "/view/MainView.fxml", "Readora Dashboard");
    }

    @FXML
    private void handleBooksTab(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Books", "You are already in the Books module.");
    }

    @FXML
    private void handleMembersTab() {
        showAlert(Alert.AlertType.INFORMATION, "Members", "Members page will be added soon.");
    }

    @FXML
    private void handleBorrowRecordsTab() {
        showAlert(Alert.AlertType.INFORMATION, "Borrow Records", "Borrow Records page will be added soon.");
    }

    @FXML
    private void handleReportsTab() {
        showAlert(Alert.AlertType.INFORMATION, "Reports", "Reports page will be added soon.");
    }

    @FXML
    private void handleOpenAddPopup() {
        editingBook = null;
        popupTitleLabel.setText("Add New Book");
        clearPopupFields();
        popupOverlay.setVisible(true);
        popupOverlay.setManaged(true);
    }

    private void openEditPopup(Book book) {
        editingBook = book;
        popupTitleLabel.setText("Edit Book");
        bookIdField.setText(book.getBookId());
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        categoryComboBox.setValue(book.getCategory());
        statusComboBox.setValue(book.getStatus());
        popupOverlay.setVisible(true);
        popupOverlay.setManaged(true);
    }

    @FXML
    private void handleSaveBook() {
        if (isInputInvalid()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please complete all book fields.");
            return;
        }

        if (editingBook == null) {
            Book newBook = new Book(
                    bookIdField.getText().trim(),
                    titleField.getText().trim(),
                    authorField.getText().trim(),
                    categoryComboBox.getValue(),
                    statusComboBox.getValue()
            );
            masterBookList.add(newBook);
        } else {
            editingBook.setBookId(bookIdField.getText().trim());
            editingBook.setTitle(titleField.getText().trim());
            editingBook.setAuthor(authorField.getText().trim());
            editingBook.setCategory(categoryComboBox.getValue());
            editingBook.setStatus(statusComboBox.getValue());
            bookTable.refresh();
        }

        applyFilters();
        hidePopup();
    }

    @FXML
    private void handleCancelPopup() {
        hidePopup();
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

    private void hidePopup() {
        clearPopupFields();
        popupOverlay.setVisible(false);
        popupOverlay.setManaged(false);
    }

    private void clearPopupFields() {
        bookIdField.clear();
        titleField.clear();
        authorField.clear();
        categoryComboBox.setValue(null);
        statusComboBox.setValue(null);
        editingBook = null;
    }

    private void handleLogout(ActionEvent event) {
        switchScene(event, "/view/LoginView.fxml", "Readora - Login");
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