package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PaymentController {
    @FXML
    private TextField cb;

    @FXML
    private Label discount;

    @FXML
    private TextField email;

    @FXML
    private Label final_p;

    @FXML
    private ImageView logo;

    @FXML
    private Label nb_tickets;

    @FXML
    private Label subtotal1;

    @FXML
    private Label subtotal2;

    @FXML
    private ImageView poster;

    @FXML
    private Label titre_movie;

    public void setPayment(String email2, String cb2, String post, String title, int nb_tick){
        email.setText(email2);
        cb.setText(cb2);
        poster.setImage(new Image(post));
        titre_movie.setText(title);
        nb_tickets.setText(""+nb_tick);

    }
}

