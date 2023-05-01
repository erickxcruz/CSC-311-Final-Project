/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author erickcruz
 */
public class TertiaryController {

    @FXML
    private List<Movie> movies = new ArrayList<>();
    @FXML
    private ListView listview;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField startTimeTextField;
    @FXML
    private TextField endTimeTextField;

    /**
     * This is the openConnection Method which allows the database to be
     * accessed using the JDBC client driver.
     *
     * @return conn, An instance of Connection.
     */
    private static Connection openConnection() {
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

    @FXML
    private void loadMoviesFromJSONFile() throws FileNotFoundException {
        Connection conn;
        conn = openConnection();
        //Creating a filechooser insatnce and adding a filter to it so we onnly get json files.
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File current = null;
        try {
            current = new File(new File(".").getCanonicalPath()); // Current working directory
        } catch (IOException e1) {
            System.out.println("Did not work");
        }
        //This code opens up the directory
        fileChooser.setInitialDirectory(current);
        File selectedFile = fileChooser.showOpenDialog(null);
        //If statement checks if the file that was selected is not empty.
        if (selectedFile != null) {
            //Instance of the gson class
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            FileReader fr = new FileReader(selectedFile);

            Movie[] ma = gson.fromJson(fr, Movie[].class);
            // Using an enhanced for loop to go through each book in the array list from the JSON file.
            for (Movie movie : ma) {
                try {
                    movies.add(movie);
                    String sql = "INSERT INTO Movies (ID, Title, StartTime, EndTime) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setInt(1, movie.getId());
                    preparedStatement.setString(2, movie.getTitle());
                    preparedStatement.setString(3, movie.getStartTime());
                    preparedStatement.setString(4, movie.getEndTime());
                    int row = preparedStatement.executeUpdate();
                    if (row > 0) {
                        System.out.println("Row inserted");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @FXML
    private void exportToJSONFile() {
        Connection conn = openConnection();

        //Creating a filechooser instance and adding a filter to it so we only get JSON files.
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File current = null;
        try {
            current = new File(new File(".").getCanonicalPath()); // Current working directory
        } catch (IOException e1) {
            System.out.println("Did not work");
        }
        //This code opens up the directory
        fileChooser.setInitialDirectory(current);
        File selectedFile = fileChooser.showSaveDialog(null);
        //If statement checks if the file that was selected is not empty.
        if (selectedFile != null) {
            try {
                // Query the database to retrieve all movies
                String query = "SELECT * FROM Movies";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery();

                // Iterate through the result set and add each movie to the list
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String title = rs.getString("Title");
                    String startTime = rs.getString("StartTime");
                    String endTime = rs.getString("EndTime");
                    Movie movie = new Movie(id, title, startTime, endTime);
                    movies.add(movie);
                }

                // Create an instance of the Gson class
                Gson gson = new Gson();

                // Convert the list of movies to a JSON array
                JsonArray jsonArray = gson.toJsonTree(movies).getAsJsonArray();

                // Write the JSON array to the selected file
                FileWriter fileWriter = new FileWriter(selectedFile);
                gson.toJson(jsonArray, fileWriter);
                fileWriter.close();

                System.out.println("Exported to file: " + selectedFile.getAbsolutePath());
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addMovieToDatabase() {
        Connection conn;
        conn = openConnection();
        try {
            String sql = "INSERT INTO Movies (ID, Title, StartTime, EndTime) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(idTextField.getText()));
            preparedStatement.setString(2, titleTextField.getText());
            preparedStatement.setString(3, startTimeTextField.getText());
            preparedStatement.setString(4, endTimeTextField.getText());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {

                System.out.println("Movie added to database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadMoviesFromDatabase() {
        // create an observable list to hold the movie data
        ObservableList<Movie> movieList = FXCollections.observableArrayList();

        // establish a connection to the database using JDBC
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password")) {
            // create an SQL statement to select the movies from the database
            String sql = "SELECT * FROM Movies";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            // execute the SQL statement and retrieve the results as a ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // loop through the ResultSet and add the data to the observable list
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String title = resultSet.getString("Title");
                String startTime = resultSet.getString("StartTime");
                String endTime = resultSet.getString("EndTime");

                Movie movie = new Movie(id, title, startTime, endTime);
                movieList.add(movie);
            }

            // set the observable list as the data source for the ListView
            listview.setItems(movieList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
