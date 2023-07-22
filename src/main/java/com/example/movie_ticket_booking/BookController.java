package com.example.movie_ticket_booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookController {

    @FXML
    public ComboBox<String> date_hour;

    @FXML
    private Button sbm_btn;

    @FXML
    private ImageView film_img;

    @FXML
    public Label film_titre;

    @FXML
    private Button lock_venue;

    @FXML
    private Button lock_hour;

    @FXML
    private Label txtPrice;

    @FXML
    private TextField place_nb;

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
    private Label txtSynopsys;

    public int cpt_tickets = 0;

    private int sessionId;

    @FXML
    public ComboBox<String> venue_cb;

    private int account;
    private final int[] tab = new int[30];

    public void setBook(String post, String nom, ObservableList<String> items, ObservableList<String> items2, int account, String synopsys){
        this.account=account;
        film_img.setImage(new Image(post));
        film_titre.setText(nom);
        venue_cb.setItems(items);
        date_hour.setItems(items2);
        txtSynopsys.setText("Synopsis: " + synopsys);
    }


    public void lock_cinema() {
        venue_cb.setDisable(true);
    }

    public void combo_cinema() {
        String selectedCinema = venue_cb.getSelectionModel().getSelectedItem();
        ObservableList<String> updatedItems2 = FXCollections.observableArrayList();
        int price=0;

        if (!venue_cb.isDisable()) {
            try {
                Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat2 = con2.createStatement();
                ResultSet rs2 = stat2.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie JOIN cinema on session.Id_cinema=cinema.Id_cinema WHERE movie.Name='" + film_titre.getText() + "' AND cinema.name='" + selectedCinema + "' ");
                while (rs2.next()) {
                    price=rs2.getInt("session.Price");
                    String date = rs2.getString("Date");
                    if (!rs2.wasNull()) {
                        updatedItems2.add(date);
                    }
                }
                date_hour.setItems(updatedItems2);
                lock_cinema();
                stat2.close();
                con2.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (date_hour.isDisable() && venue_cb.isDisable()) {
            txtPrice.setText("Price: £"+price);
            setSeatButtons();
        }
    }

    public void lock_date() {
        date_hour.setDisable(true);
    }

    public void combo_date() {
        String selectedDate = date_hour.getSelectionModel().getSelectedItem();
        Set<String> uniqueCinemas = new HashSet<>();
        int price=0;

        if (!date_hour.isDisable()) {
            try {
                Connection con3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat3 = con3.createStatement();
                ResultSet rs3 = stat3.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie JOIN cinema on session.Id_cinema=cinema.Id_cinema WHERE movie.Name='" + film_titre.getText() + "' AND session.date='" + selectedDate + "' ");

                while (rs3.next()) {
                    price=rs3.getInt("session.Price");
                    String cinema = rs3.getString("cinema.name");
                    if (!rs3.wasNull()) {
                        uniqueCinemas.add(cinema);
                    }
                }

                venue_cb.setItems(FXCollections.observableArrayList(uniqueCinemas));
                lock_date();

                con3.close();
                stat3.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (date_hour.isDisable() && venue_cb.isDisable()) {
            txtPrice.setText("Price: £"+price);
            setSeatButtons();
        }
    }


    public void setSeatButtons() {
        if (date_hour.isDisable() && venue_cb.isDisable()) {
            try {
                String selectedDate = date_hour.getSelectionModel().getSelectedItem();
                String selectedCinema = venue_cb.getSelectionModel().getSelectedItem();

                Connection con4 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat4 = con4.createStatement();
                ResultSet rs4 = stat4.executeQuery("SELECT ID_session, ID_room, seat_1, seat_2, seat_3, seat_4, seat_5, seat_6, seat_7, seat_8, seat_9, seat_10, seat_11, seat_12, seat_13, seat_14, seat_15, seat_16, seat_17, seat_18, seat_19, seat_20, seat_21, seat_22, seat_23, seat_24, seat_25, seat_26, seat_27, seat_28, seat_29, seat_30 FROM session JOIN cinema ON session.Id_cinema = cinema.Id_cinema WHERE session.date = '" + selectedDate + "' AND cinema.name = '" + selectedCinema + "'");

                if (rs4.next()) {
                    sessionId = rs4.getInt("ID_session");

                    List<Integer> reservedSeats = new ArrayList<>();

                    for (int seatNumber = 1; seatNumber <= 30; seatNumber++) {
                        String seatColumnName = "seat_" + seatNumber;
                        int seatValue = rs4.getInt(seatColumnName);

                        if (seatValue == 1) {
                            reservedSeats.add(seatNumber);
                        }

                        String seatButtonId = "s" + seatNumber;
                        Button seatButton = (Button) getClass().getDeclaredField(seatButtonId).get(this);

                        seatButton.setDisable(seatValue == 1);
                    }
                }

                rs4.close();
            } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void seats(ActionEvent event) {
        String selectedDate = date_hour.getSelectionModel().getSelectedItem();
        String selectedCinema = venue_cb.getSelectionModel().getSelectedItem();


            if (selectedDate != null && selectedCinema != null) {
                try {
                    Connection con4 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                    Statement stat4 = con4.createStatement();
                    ResultSet rs4 = stat4.executeQuery("SELECT * FROM session WHERE Date = '" + selectedDate + "' AND Id_cinema = (SELECT Id_cinema FROM cinema WHERE Name = '" + selectedCinema + "')");

                    if (rs4.next()) {
                        Button seatButton = (Button) event.getSource();
                        int seatNumber = Integer.parseInt(seatButton.getText());

                        for (int i = 0; i < 30; i++) {
                            if(i==seatNumber-1 || rs4.getInt("seat_"+(i+1))==1)
                                tab[i] = 1;
                        }

                        seatButton.setDisable(true);
                        cpt_tickets++;

                    }
                    rs4.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

    }

    @FXML
    void book_now(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
        Parent root = fxmlLoader.load();
        Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        HomeController hc = fxmlLoader.getController();
        hc.Initialisation(account);

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie WHERE session.ID_session='"+sessionId+"'");

        while (rs.next()) {
            hc.payment(sessionId, rs.getString("ID_movie"),cpt_tickets,tab);
        }

        rs.close();
        stat.close();
        con.close();

        Scene scene = new Scene(root);
        lstage.setScene(scene);
        lstage.show();
    }

}
