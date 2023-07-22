package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SalesCinemaController {

    @FXML
    private Label txtIncome;

    @FXML
    private Label txtName;

    @FXML
    private Label txtPlaces;


    public void setSalesMovie(String name,int income, int place)
    {
        //sets the sales information for a movie
        txtName.setText(name);
        txtPlaces.setText(place + " seats");
        txtIncome.setText("£ " + income);
    }

}
