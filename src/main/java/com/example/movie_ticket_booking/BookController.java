package com.example.movie_ticket_booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
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

    public int cpt_tickets = 0;

    private int sessionId;

    @FXML
    public ComboBox<String> venue_cb;
    public Connection con4 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
    public Statement stat4 = con4.createStatement();
    public Connection con3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
    public Statement stat3 = con3.createStatement();
    public Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
    public Statement stat2 = con2.createStatement();

    private int account;

    public BookController() throws SQLException {
    }


    public void setBook(String post, String nom, ObservableList items, ObservableList items2, int account) throws SQLException {
        this.account=account;
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
        if (!venue_cb.isDisable()) {
            try {
                ResultSet rs2 = stat2.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie JOIN cinema on session.Id_cinema=cinema.Id_cinema WHERE movie.Name='" + film_titre.getText() + "' AND cinema.name='" + selectedCinema + "' ");
                while (rs2.next()) {
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
            setSeatButtons();
        }
    }

    public void lock_date() {
        date_hour.setDisable(true);
    }

    public void combo_date() {
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
                lock_date();
                con3.close();
                stat3.close();
            } catch (Exception e) {
                System.out.println("zizi");
            }
        }
        if (date_hour.isDisable() && venue_cb.isDisable()) {
            setSeatButtons();
        }
    }

    public void setSeatButtons() {
        if (date_hour.isDisable() && venue_cb.isDisable()) {
            try {
                String selectedDate = date_hour.getSelectionModel().getSelectedItem();
                String selectedCinema = venue_cb.getSelectionModel().getSelectedItem();

                ResultSet rs4 = stat4.executeQuery("SELECT ID_session FROM session JOIN cinema ON session.Id_cinema = cinema.Id_cinema WHERE session.date = '" + selectedDate + "' AND cinema.name = '" + selectedCinema + "'");

                if (rs4.next()) {
                    int sessionId = rs4.getInt("ID_session");

                    ResultSet rs5 = stat4.executeQuery("SELECT Seat_nb FROM seats WHERE Id_room = (SELECT Id_room FROM session WHERE ID_session = " + sessionId + ") AND reserved = 1");

                    Set<Integer> reservedSeats = new HashSet<>();

                    while (rs5.next()) {
                        int seatNumber = rs5.getInt("Seat_nb");
                        reservedSeats.add(seatNumber);
                    }

                    rs5.close();

                    for (int seatNumber = 1; seatNumber <= 30; seatNumber++) {
                        String seatButtonId = "s" + seatNumber;
                        Button seatButton = (Button) getClass().getDeclaredField(seatButtonId).get(this);

                        if (reservedSeats.contains(seatNumber)) {
                            seatButton.setDisable(true);
                        } else {
                            seatButton.setDisable(false);
                        }
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
        if (date_hour.isDisable() && venue_cb.isDisable()) {
            if (selectedDate != null && selectedCinema != null) {
                try {
                    ResultSet rs4 = stat4.executeQuery("SELECT ID_session FROM session JOIN cinema ON session.Id_cinema = cinema.Id_cinema WHERE session.date = '" + selectedDate + "' AND cinema.name = '" + selectedCinema + "'");

                    if (rs4.next()) {
                        sessionId = rs4.getInt("ID_session");
                        Button seatButton = (Button) event.getSource();
                        int seatNumber = Integer.parseInt(seatButton.getText());

                        ResultSet rs5 = stat4.executeQuery("SELECT reserved FROM seats WHERE Id_room = (SELECT Id_room FROM session WHERE ID_session = " + sessionId + ") AND Seat_nb = " + seatNumber);

                        if (rs5.next()) {
                            boolean reserved = rs5.getBoolean("reserved");

                            if (!reserved) {

                                String request = "UPDATE seats SET reserved = 1 WHERE Id_room = (SELECT Id_room FROM session WHERE ID_session = " + sessionId + ") AND Seat_nb = " + seatNumber;
                                stat4.executeUpdate(request);
                                seatButton.setDisable(true);
                                cpt_tickets++;
                            } else {
                                System.out.println("La place est déjà réservée !");
                            }
                        }
                        rs5.close();
                    }
                    rs4.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    @FXML
    void book_now(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader2 = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader2.load());

        HomeController hc = fxmlLoader2.getController();
        hc.Initialisation(account);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie WHERE session.ID_session='"+sessionId+"'");

        if (rs.next()) {
            hc.payment(sessionId, rs.getString("ID_movie"),cpt_tickets);

        } else {
            System.out.println("Aucun résultat trouvé pour la session ID " + sessionId);
        }

        rs.close();
        stat.close();
        con.close();
    }

}
