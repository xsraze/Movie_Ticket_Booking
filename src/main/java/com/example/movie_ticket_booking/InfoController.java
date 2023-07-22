package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InfoController {
    @FXML
    private Label txtEmail;

    @FXML
    private VBox VbReservation;
    @FXML
    private Label txtLastName;

    @FXML
    private Label txtName;

    @FXML
    private Label txtPassword;

    @FXML
    private Label txtPhone;

    @FXML
    private Label txtType;

    @FXML
    private Label txtUsername;
    @FXML
    private Label txtWelcome;

    public void SetInfo(int account){
        //information of the person logged in
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `users`");

            while (rs.next())
            {
                //every information needed
                if(account == Integer.parseInt(rs.getString("ID_user")))
                {
                    txtWelcome.setText("Welcome "+rs.getString("Name")+"!");
                    txtType.setText(rs.getString("Type"));
                    txtPhone.setText(rs.getString("phone"));
                    txtUsername.setText(rs.getString("username"));
                    txtName.setText(rs.getString("Name"));
                    txtLastName.setText(rs.getString("LastName"));
                    txtEmail.setText(rs.getString("email"));
                    txtPassword.setText(rs.getString("password"));
                }

            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            //every reservation of a user
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM users JOIN reservation ON reservation.Email_adress=users.email JOIN session ON reservation.ID_session=session.ID_session JOIN movie ON session.ID_movie=movie.ID_movie JOIN cinema ON cinema.Id_cinema=session.Id_cinema WHERE reservation.Email_adress = "+'"'+txtEmail.getText()+'"');

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Reservation.fxml"));
                VBox Reservation = fxmlLoader.load();
                ReservationController rc = fxmlLoader.getController();
                rc.setResa(rs.getString("movie.Name"), rs.getString("movie.Author"), rs.getString("movie.Year"), rs.getString("cinema.name"), rs.getString("session.Date"), rs.getString("reservation.Nb_tickets"), rs.getString("reservation.Card_number"), rs.getString("reservation.ID_room"), rs.getString("reservation.FinalPrice"), rs.getString("movie.poster"));

                VbReservation.getChildren().add(Reservation);
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }
}
