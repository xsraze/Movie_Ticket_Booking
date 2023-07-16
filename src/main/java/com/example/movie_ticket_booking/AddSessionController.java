package com.example.movie_ticket_booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddSessionController {

    @FXML
    private Label ErrDate;

    @FXML
    private Label ErrDiscount;

    @FXML
    private Label ErrMovie;

    @FXML
    private Label ErrPrice;

    @FXML
    private Label ErrRoom;

    @FXML
    private Label ErrVenue;

    @FXML
    private ComboBox<?> comboRoom;

    @FXML
    private ComboBox<?> comboVenue;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtMovie;

    @FXML
    private TextField txtPrice;

    @FXML
    void AddAgain(ActionEvent event) {

    }

    @FXML
    void AddExit(ActionEvent event) {

    }

}
