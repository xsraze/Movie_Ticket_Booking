package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class HomeController {
    @FXML
    private ComboBox<?> combo_date;

    @FXML
    private ComboBox<?> combo_movie;

    @FXML
    private ComboBox<?> combo_seat;

    @FXML
    private ComboBox<?> combo_venue;

    @FXML
    private Button login_btn;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView logo1;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button search_qb;

    @FXML
    private TextField searchbar;

    @FXML
    private Button signin_btn;
}
