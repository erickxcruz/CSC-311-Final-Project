package com.mycompany.assignment4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        Connection conn = openConnection();
        launch();
    }

    /**
     * This is the openConnection Method which allows the database to be
     * accessed using the JDBC client driver.
     *
     * @return conn, An instance of Connection.
     */
    public static Connection openConnection() {
        String databaseURL = "";
        Connection conn = null;

        try {
            databaseURL = "jdbc:ucanaccess://.//CineMate.accdb";
            conn = DriverManager.getConnection(databaseURL);
            System.out.println("Connected");
        } catch (SQLException ex) {
        }
        return conn;
    }

}
