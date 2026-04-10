package com.readora.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class BorrowRecordsController {

    @FXML
    private TableView<Object> borrowTable; // Replace Object with your actual BorrowRecord model class

    @FXML
    private TableColumn<Object, Integer> colId;
    @FXML
    private TableColumn<Object, String> colBook;
    @FXML
    private TableColumn<Object, String> colMember;
    @FXML
    private TableColumn<Object, LocalDate> colBorrow;
    @FXML
    private TableColumn<Object, LocalDate> colDue;
    @FXML
    private TableColumn<Object, LocalDate> colReturn;

    @FXML
    public void initialize() {
        // These strings must match the variable names in your BorrowRecord.java model
        colId.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        colBook.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colMember.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colBorrow.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colDue.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        loadData();
    }

    private void loadData() {
        // Logic to fetch records from your database or ObservableList
    }

    @FXML
    private void handleUpdateStatus() {
        // Logic for returning a book or marking as overdue
        System.out.println("Updating record status...");
    }

    @FXML
    private void handleClose() {
        // Logic to close the current view or return to the dashboard
    }
}