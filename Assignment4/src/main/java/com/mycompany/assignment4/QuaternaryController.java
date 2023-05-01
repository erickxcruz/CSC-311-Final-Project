/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment4;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class This is the controller for the sign up page.
 *
 * @author erickcruz
 */
public class QuaternaryController {

    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label registrationLabel;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void registerUsersButtonHandler() throws SQLException {
        // Check if any field is empty
        if (fullNameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty() || userNameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            registrationLabel.setText("Please fill all fields.");
            return;
        }
        String name = fullNameTextField.getText();
        String email = emailTextField.getText();
        String phone = phoneNumberTextField.getText();
        String username = userNameTextField.getText();
        String password = passwordTextField.getText();

        // Check email and phone number format
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String phoneNumberRegex = "^\\d{10}$";
        if (!email.matches(emailRegex) || !phone.matches(phoneNumberRegex)) {
            registrationLabel.setText("Please enter a valid email and phone number.");
            return;
        }

        // Insert new entry into database
        String url = "jdbc:ucanaccess://CineMate.accdb";
        String sql = "INSERT INTO Users (FullName, Email, PhoneNumber, Username, Password) VALUES (?, ?, ?, ?, ?)";
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try (Connection conn = DriverManager.getConnection(url); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, username);
                stmt.setString(5, password);

                int affectedRows = stmt.executeUpdate();

                Platform.runLater(() -> {
                    if (affectedRows == 1) {
                        registrationLabel.setText("Successful Registration.");
                    } else {
                        registrationLabel.setText("Registration failed. Please try again.");
                    }
                });
            } catch (SQLException ex) {
                Platform.runLater(() -> {
                    registrationLabel.setText("Registration failed. Please try again.");
                });
                ex.printStackTrace();
            }
        });

        // Shutdown the executor to release resources
        executor.shutdown();
    }

    @FXML
    private void registerEmployeesButtonHandler() {
        String name = fullNameTextField.getText();
        String email = emailTextField.getText();
        String phone = phoneNumberTextField.getText();
        String username = userNameTextField.getText();
        String password = passwordTextField.getText();

        // Check if any field is empty
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            registrationLabel.setText("Please fill all fields.");
            return;
        }

        // Check email and phone number format
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String phoneNumberRegex = "^\\d{10}$";
        if (!email.matches(emailRegex) || !phone.matches(phoneNumberRegex)) {
            registrationLabel.setText("Please enter a valid email and phone number.");
            return;
        }

        // Insert new entry into database
        String url = "jdbc:ucanaccess://CineMate.accdb";
        String sql = "INSERT INTO Employees (FullName, Email, PhoneNumber, Username, Password) VALUES (?, ?, ?, ?, ?)";
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try (Connection conn = DriverManager.getConnection(url); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, username);
                stmt.setString(5, password);

                int affectedRows = stmt.executeUpdate();

                Platform.runLater(() -> {
                    if (affectedRows == 1) {
                        registrationLabel.setText("Successful Registration.");
                    } else {
                        registrationLabel.setText("Registration failed. Please try again.");
                    }
                });
            } catch (SQLException ex) {
                Platform.runLater(() -> {
                    registrationLabel.setText("Registration failed. Please try again.");
                });
                ex.printStackTrace();
            }
        });

        // Shutdown the executor to release resources
        executor.shutdown();
    }

}
