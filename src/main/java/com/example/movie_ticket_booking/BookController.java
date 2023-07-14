package com.example.movie_ticket_booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.*;

public class BookController {

    @FXML
    public ComboBox<String> date_hour;

    @FXML
    private ImageView film_img;

    @FXML
    public Label film_titre;

    @FXML
    private Button min_btn;
    @FXML
    private Button lock_venue;

    @FXML
    private Button lock_hour;

    @FXML
    private TextField place_nb;

    @FXML
    private Button plus_btn;

    @FXML
    private Button s1;

    @FXML
    private Button s10;

    @FXML
    private Button s11;

    @FXML
    private Button s12;

    @FXML
    private Button s13;

    @FXML
    private Button s14;

    @FXML
    private Button s15;

    @FXML
    private Button s16;

    @FXML
    private Button s17;

    @FXML
    private Button s18;

    @FXML
    private Button s19;

    @FXML
    private Button s2;

    @FXML
    private Button s20;

    @FXML
    private Button s21;

    @FXML
    private Button s22;

    @FXML
    private Button s23;

    @FXML
    private Button s24;

    @FXML
    private Button s25;

    @FXML
    private Button s26;

    @FXML
    private Button s27;

    @FXML
    private Button s28;

    @FXML
    private Button s29;

    @FXML
    private Button s3;

    @FXML
    private Button s30;

    @FXML
    private Button s4;

    @FXML
    private Button s5;

    @FXML
    private Button s6;

    @FXML
    private Button s7;

    @FXML
    private Button s8;

    @FXML
    private Button s9;

    @FXML
    public ComboBox<String> venue_cb;
    public Connection con3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
    public Statement stat3 = con3.createStatement();
    public Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
    public Statement stat2 = con2.createStatement();

    public BookController() throws SQLException {
    }


    public void setBook(String post, String nom, ObservableList items, ObservableList items2) throws SQLException {

        film_img.setImage(new Image(post));
        film_titre.setText(nom);
        venue_cb.setItems(items);
        date_hour.setItems(items2);
    }

    public void lock_cinema() {
        venue_cb.setDisable(true);
    }

    public void combo_cinema() {
        String selectedCinema = venue_cb.getSelectionModel().getSelectedItem();
        ObservableList<String> updatedItems2 = FXCollections.observableArrayList();
        if(!venue_cb.isDisable()){
            try {
                ResultSet rs2 = stat2.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie JOIN cinema on session.Id_cinema=cinema.Id_cinema WHERE movie.Name='" + film_titre.getText() + "' AND cinema.name='" + selectedCinema + "' ");

                while (rs2.next()) {
                    String date = rs2.getString("Date");
                    if (!rs2.wasNull()) {
                        updatedItems2.add(date);
                    }
                }

                date_hour.setItems(updatedItems2);
                stat2.close();
                con2.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void lock_date() {
        date_hour.setDisable(true);
    }

    public void combo_date(){
        String selectedDate = date_hour.getSelectionModel().getSelectedItem();
        ObservableList<String> updatedItems = FXCollections.observableArrayList();
        if (!date_hour.isDisable()) {
            try {
                ResultSet rs3 = stat3.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie JOIN cinema on session.Id_cinema=cinema.Id_cinema WHERE movie.Name='" + film_titre.getText() + "' AND session.date='" + selectedDate + "' ");

                while (rs3.next()) {
                    String cinema = rs3.getString("cinema.name");
                    if (!rs3.wasNull()) {
                        updatedItems.add(cinema);
                    }
                }
                venue_cb.setItems(updatedItems);
                con3.close();
                stat3.close();

            } catch (Exception e) {
                System.out.println("zizi");
            }
        }
    }
}
