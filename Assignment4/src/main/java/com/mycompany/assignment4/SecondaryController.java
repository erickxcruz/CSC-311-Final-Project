package com.mycompany.assignment4;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SecondaryController {

    @FXML
    private ImageView sMBImage;

    @FXML
    private ImageView jWImage;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToSMBMovie() throws IOException {
        App.setRoot("SMBMovie");
    }

    @FXML
    private void switchToJWMovie() throws IOException {
        App.setRoot("JWMovie");
    }

}
