module com.mycompany.assignment4 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.assignment4 to javafx.fxml;
    exports com.mycompany.assignment4;
}
