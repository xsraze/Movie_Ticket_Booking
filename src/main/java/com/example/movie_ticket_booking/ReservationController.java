package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ReservationController {

    @FXML
    private Label TxtRoom;

    @FXML
    private ImageView imgPoster;

    @FXML
    private Label txtCard;

    @FXML
    private Label txtCinema;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtPrice;

    @FXML
    private Label txtRealisator;

    @FXML
    private Label txtTickets;

    @FXML
    private Label txtTitle;

    @FXML
    private Label txtRoom;

    @FXML
    private Label txtYear;

    public void setResa(String movie, String realisator, String year, String cinema, String date, String tickets, String card, String room, String price, String poster)
    {
        txtTitle.setText(movie);
        txtRealisator.setText(realisator);
        txtYear.setText(year);
        txtCinema.setText(cinema);
        txtDate.setText(date);
        txtTickets.setText("x" + tickets);
        txtCard.setText(card);
        txtRoom.setText(room);
        txtPrice.setText(price);
        imgPoster.setImage(new Image(poster));
    }

}
