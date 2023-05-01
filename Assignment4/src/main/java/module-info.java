module com.mycompany.assignment4 {
    requires com.google.gson;
    requires javafx.controls;
    requires java.sql; 
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.assignment4 to javafx.fxml, com.google.gson;
    exports com.mycompany.assignment4;
}
