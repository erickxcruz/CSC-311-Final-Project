package com.mycompany.assignment4;

import java.io.IOException;
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
    private Button logInButtonWorker;

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
        logInButtonWorker.setOpacity(0);
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
        logInButtonWorker.setOpacity(1);
        signUpButton.setOpacity(1);
        imageLogo.setOpacity(1);
        
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void checkLoginCredentials() {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (!emailTextField.getText().isBlank() || !passwordTextField.getText().isBlank()) {

            String email = emailTextField.getText();

            Matcher matcher = pattern.matcher(email);
            int count = 0;
            /*
            while (matcher.matches() == false) {
                count++;
                if (count == 3) {

                }

            }
             */

            try {
                switchToSecondary();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(email + " : " + matcher.matches() + "\n");
            String password = passwordTextField.getText();
            System.out.println(password);
        } else {

        }
    }

    @FXML
    private void switchToTertiary() throws IOException {
        App.setRoot("tertiary");
    }
    
    @FXML
    private void checkWorkerLoginCredentials()  {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (!emailTextField.getText().isBlank() || !passwordTextField.getText().isBlank()) {

            String email = emailTextField.getText();

            Matcher matcher = pattern.matcher(email);
            int count = 0;
            while (matcher.matches() == false) {
                count++;
                if (count == 3) {

                }

            }

            try {
                switchToTertiary();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(email + " : " + matcher.matches() + "\n");
            String password = passwordTextField.getText();
            System.out.println(password);
        } else {

        }
    }
    
    @FXML
    private void switchToQuaternary() throws IOException {
        App.setRoot("quaternary");
    }

}
