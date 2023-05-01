/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment4;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Erick
 */
public class SMBMovieController  {
    @FXML
    private GridPane gridPane;
    
    @FXML
    private Label statusLabel;
    
    // Seats
    @FXML
    private Button seat1;
    @FXML
    private Button seat2;
    @FXML
    private Button seat3;
    @FXML
    private Button seat4;
    @FXML
    private Button seat5;
    @FXML
    private Button seat6;
    @FXML
    private Button seat7;
    @FXML
    private Button seat8;
    @FXML
    private Button seat9;
    @FXML
    private Button seat10;
    @FXML
    private Button seat11;
    @FXML
    private Button seat12;
    @FXML
    private Button seat13;
    @FXML
    private Button seat14;
    @FXML
    private Button seat15;
    @FXML
    private Button seat16;
    @FXML
    private Button seat17;
    @FXML
    private Button seat18;
    @FXML
    private Button seat19;
    @FXML
    private Button seat20;
    
    private List<Boolean> seatStatus = new ArrayList<>(); // true: seat is booked, false: seat is available
    
    @FXML
    private void initialize() {
        // Initialize seat status to all false (available)
        for (int i = 0; i < 20; i++) {
            seatStatus.add(false);
        }
    }
    
    @FXML
    private void toggleSeat() {
        Button seat = (Button) gridPane.getScene().getFocusOwner();
        int seatIndex = Integer.parseInt(seat.getText()) - 1;
        seatStatus.set(seatIndex, !seatStatus.get(seatIndex));
        updateSeatStyle(seat);
    }
    
    private void updateSeatStyle(Button seat) {
        int seatIndex = Integer.parseInt(seat.getText()) - 1;
        if (seatStatus.get(seatIndex)) {
            seat.setStyle("-fx-background-color: #ff0000;"); // red
        } else {
            seat.setStyle(""); // reset style to default
        }
    }
    
    @FXML
    private void bookSelectedSeats() {
        String bookedSeats = "";
        int numBooked = 0;
        for (int i = 0; i < seatStatus.size(); i++) {
            if (seatStatus.get(i)) {
                bookedSeats += (i + 1) + ", ";
                numBooked++;
            }
        }
        if (numBooked > 0) {
            bookedSeats = bookedSeats.substring(0, bookedSeats.length() - 2);
            statusLabel.setText(numBooked + " seats booked: " + bookedSeats);
        } else {
            statusLabel.setText("No seats selected.");
        }
    }
}

     
   
