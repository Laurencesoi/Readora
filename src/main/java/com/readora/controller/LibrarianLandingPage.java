package com.readora.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LibrarianLandingPage {

    @FXML
    private void searchBook(ActionEvent event) {
        System.out.println("Librarian executing: searchBook");
    }

    @FXML
    private void manageMember(ActionEvent event) {
        System.out.println("Librarian executing: manageMember");
    }

    @FXML
    private void borrowBook(ActionEvent event) {
        System.out.println("Librarian executing: borrowBook");
    }

    @FXML
    private void returnBook(ActionEvent event) {
        System.out.println("Librarian executing: returnBook");
    }

    @FXML
    private void viewBorrowingRecords(ActionEvent event) {
        System.out.println("Librarian executing: viewBorrowingRecords");
    }
}