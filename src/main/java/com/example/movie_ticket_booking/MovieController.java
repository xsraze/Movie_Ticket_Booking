package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MovieController {
    @FXML
    private Label Year;

    @FXML
    private Label Genre;

    @FXML
    private Label Name;

    @FXML
    private ImageView poster;

    public void SetMovie(String post, String Gen, String Nam, String Yea){
        poster.setImage(new Image(post));
        Name.setText(Nam);
        Genre.setText(Gen);
        Year.setText(Yea);
    }
}
