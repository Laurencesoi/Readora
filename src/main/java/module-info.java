module com.example.readora {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.readora to javafx.fxml;
    exports com.readora;
    exports com.readora.controller;
    opens com.readora.controller to javafx.fxml;
}