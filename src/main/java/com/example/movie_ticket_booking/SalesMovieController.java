package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SalesMovieController {
    @FXML
    private Label txtGenre;

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


    public void setSalesMovie(String name, String Realisator, int year, String genre)
    {
        txtTitle.setText(name);
        txtGenre.setText(genre);
        txtRealisator.setText(Realisator);
        txtYear.setText(String.valueOf(year));

    }

}
