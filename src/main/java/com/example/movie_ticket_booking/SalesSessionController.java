package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SalesSessionController {
    @FXML
    private ImageView imgPoster;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtIncome;

    @FXML
    private Label txtMovie;

    @FXML
    private Label txtPlaces;

    @FXML
    private Label txtRoom;

    @FXML
    private Label txtVenue;

    public void SetSaleSession(String movie, String cinema, String date, String room, int place, int income, String poster){
        txtDate.setText(date);
        txtMovie.setText(movie);
        txtVenue.setText(cinema);
        txtRoom.setText(room);
        txtIncome.setText("£ " + income);
        txtPlaces.setText(place + " seats");
        imgPoster.setImage(new Image(poster));
    }
}

