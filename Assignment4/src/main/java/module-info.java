module com.mycompany.assignment4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.assignment4 to javafx.fxml;
    exports com.mycompany.assignment4;
}
