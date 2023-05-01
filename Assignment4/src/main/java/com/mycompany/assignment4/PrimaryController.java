package com.mycompany.assignment4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PrimaryController {

    @FXML
    private Label cineMateLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label logInErrorLabel;

    @FXML
    private Button logInButton;

    @FXML
    private Button logInButtonEmployee;

    @FXML
    private Button signUpButton;

    @FXML
    private ImageView imageLogo;

    @FXML
    private void initialize() {
        cineMateLabel.setOpacity(0);
        emailLabel.setOpacity(0);
        emailTextField.setOpacity(0);
        passwordLabel.setOpacity(0);
        passwordTextField.setOpacity(0);
        logInErrorLabel.setOpacity(0);
        logInButton.setOpacity(0);
        logInButtonEmployee.setOpacity(0);
        signUpButton.setOpacity(0);
        imageLogo.setOpacity(0);
        startUpAnimation();

    }

    @FXML
    private void startUpAnimation() {
        imageLogo.setOpacity(0);
        FadeTransition imageFade = new FadeTransition(Duration.seconds(3), imageLogo);
        imageFade.setByValue(0);
        imageFade.setToValue(100);
        imageFade.playFromStart();
        TranslateTransition imageTranslation = new TranslateTransition(Duration.seconds(3), imageLogo);
        imageTranslation.setByX(200);
        imageTranslation.playFromStart();

        ParallelTransition parallelTransition = new ParallelTransition(imageFade, imageTranslation);
        parallelTransition.setOnFinished(e -> makeTheLogInPagevisible());
        parallelTransition.playFromStart();

    }

    @FXML
    private void makeTheLogInPagevisible() {
        cineMateLabel.setOpacity(1);
        emailLabel.setOpacity(1);
        emailTextField.setOpacity(1);
        passwordLabel.setOpacity(1);
        passwordTextField.setOpacity(1);
        logInButton.setOpacity(1);
        logInButtonEmployee.setOpacity(1);
        signUpButton.setOpacity(1);
        imageLogo.setOpacity(1);

    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void checkLoginCredentials() throws SQLException {
        String userName = emailTextField.getText();
        String password = passwordTextField.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            logInErrorLabel.setText("Please enter both username and password.");
            return;
        }

        logInErrorLabel.setOpacity(1);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Boolean> loginFuture = executorService.submit(() -> {
            String url = "jdbc:ucanaccess://CineMate.accdb";
            try (Connection conn = DriverManager.getConnection(url)) {
                String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, userName);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    return rs.next();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        });

        try {
            boolean loginSuccessful = loginFuture.get();
            if (loginSuccessful) {
                try {

                    switchToSecondary();
                    // login successful
                } catch (IOException ex) {
                }
            } else {
                // login failed
                logInErrorLabel.setText("Login failed. Please check your username and password.");
            }
        } catch (InterruptedException | ExecutionException ex) {

        }

        executorService.shutdown();
    }

    @FXML
    private void switchToTertiary() throws IOException {
        App.setRoot("tertiary");
    }

    @FXML
    private void checkWorkerLoginCredentials() throws SQLException {
        String userName = emailTextField.getText();
        String password = passwordTextField.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            logInErrorLabel.setText("Please enter both username and password.");
            return;
        }

        logInErrorLabel.setOpacity(1);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Boolean> loginFuture = executorService.submit(() -> {
            String url = "jdbc:ucanaccess://CineMate.accdb";
            try (Connection conn = DriverManager.getConnection(url)) {
                String sql = "SELECT * FROM Employees WHERE Username = ? AND Password = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, userName);
                    stmt.setString(2, password);

                    ResultSet rs = stmt.executeQuery();
                    return rs.next();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        });

        try {
            boolean loginSuccessful = loginFuture.get();
            if (loginSuccessful) {
                try {
                    System.out.println("works");
                    switchToTertiary();
                    // login successful
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                // login failed
                logInErrorLabel.setText("Login failed. Please check your username and password.");
            }
        } catch (InterruptedException | ExecutionException ex) {

        }

        executorService.shutdown();
    }

    @FXML
    private void switchToQuaternary() throws IOException {
        App.setRoot("quaternary");
    }

}
