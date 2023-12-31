package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SalesMovieController {
    @FXML
    private Label txtGenre;

    @FXML
    private ImageView imgPoster;

    @FXML
    private Label txtIncome;

    @FXML
    private Label txtPlaces;

    @FXML
    private Label txtRealisator;

    @FXML
    private Label txtTitle;

    @FXML
    private Label txtYear;


    public void setSalesMovie(String name, String Realisator, int year, String genre, int place, int incom, String poster)
    {
        //setting the sales per movie information
        txtTitle.setText(name);
        txtGenre.setText(genre);
        txtRealisator.setText(Realisator);
        txtYear.setText(String.valueOf(year));
        txtIncome.setText("£ "+ incom);
        txtPlaces.setText(place+ " seats");
        imgPoster.setImage(new Image(poster));
    }

}
