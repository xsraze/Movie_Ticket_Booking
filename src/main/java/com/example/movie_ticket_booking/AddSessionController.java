package com.example.movie_ticket_booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
    private ComboBox<String> comboRoom;

    @FXML
    private ComboBox<String> comboVenue;

    @FXML
    private ComboBox<String> comboMovie;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtPrice;

    private int account;

    private int id_movie;
    private int id_venue;


    public void setAccount(int acc) {
        account=acc;
    }

    public void SetCombo(ObservableList<String> venue, ObservableList<String> room, ObservableList<String> movie)
    {
        //setting the items of the combo boxes
        comboRoom.setItems(room);
        comboVenue.setItems(venue);
        comboMovie.setItems(movie);
    }

    @FXML
    public void setMovie() {
        //disabling the movie combo box
        comboMovie.setDisable(true);
    }

    @FXML
    public void setVenue() {
        ObservableList<String> updatedItems = FXCollections.observableArrayList();
        if (!comboVenue.isDisable()) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs3 = stat.executeQuery("SELECT * FROM cinema JOIN room ON cinema.Id_cinema=room.Id_cinema WHERE cinema.name = "+'"'+comboVenue.getSelectionModel().getSelectedItem()+'"');
                while (rs3.next()) {
                    String room = rs3.getString("ID_room");
                    if (!rs3.wasNull()) {
                        updatedItems.add(room);
                    }
                }
                comboRoom.setItems(updatedItems);
                comboVenue.setDisable(true);
                con.close();
                stat.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void setRoom() {
        ObservableList<String> updatedItems = FXCollections.observableArrayList();
        if (!comboRoom.isDisable()) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs3 = stat.executeQuery("SELECT * FROM cinema JOIN room ON cinema.Id_cinema=room.Id_cinema WHERE room.ID_room = "+comboRoom.getSelectionModel().getSelectedItem());
                while (rs3.next()) {
                    String cinema = rs3.getString("cinema.name");
                    if (!rs3.wasNull()) {
                        updatedItems.add(cinema);
                    }
                }
                //setting the combo box and disabling the other
                comboVenue.setItems(updatedItems);
                comboRoom.setDisable(true);
                con.close();
                stat.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    @FXML
    void AddAgain(ActionEvent event) {
        //error message for the session added
        boolean error=false;

        ErrDate.setText("");
        ErrMovie.setText("");
        ErrDiscount.setText("");
        ErrPrice.setText("");
        ErrRoom.setText("");
        ErrVenue.setText("");

        if(txtDate.getText().length()==0)
        {
            //error message for the date
            ErrDate.setText("Put a Date");
            error=true;
        }
        if(txtPrice.getText().length()==0)
        {
            //error message for the price
            ErrPrice.setText("Put a Price");
            error=true;
        }
        if(txtDiscount.getText().length()==0)
        {
            //error message for the discount
            ErrDiscount.setText("Put a Discount");
            error=true;
        }
        if(!comboMovie.isDisable())
        {
            //error message for the movie
            ErrMovie.setText("Put a Movie");
            error=true;
        }
        if(!comboVenue.isDisable())
        {
            //error message for the cinema
            ErrVenue.setText("Put a Venue");
            error=true;
        }
        if(!comboRoom.isDisable())
        {
            //error message for the room
            ErrRoom.setText("Put a Room");
            error=true;
        }

        if(!error)
        {

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");

                while (rs.next())
                {
                    if(rs.getString("Name").equals(comboMovie.getSelectionModel().getSelectedItem())){
                        //catching the id movie
                        id_movie=rs.getInt("ID_movie");
                    }
                }

            }catch (Exception e1){
                System.out.println(e1.getMessage());
            }

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM `cinema`");

                while (rs.next())
                {
                    if(rs.getString("name").equals(comboVenue.getSelectionModel().getSelectedItem())){
                        //catching the id cinema
                       id_venue=rs.getInt("Id_cinema");
                    }
                }

            }catch (Exception e1){
                System.out.println(e1.getMessage());
            }

            String request;

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                boolean verified=false;

                ResultSet rs = stat.executeQuery("SELECT * FROM `session`");

                while (rs.next())
                {
                    if(txtDate.getText().equals(rs.getString("Date")) && id_venue == rs.getInt("id_cinema") && comboRoom.getSelectionModel().getSelectedItem().equals(rs.getString("ID_room")))
                    {
                        //error message for the date/cinema/room existing already
                        ErrDate.setText("This Session already exists");
                        ErrVenue.setText("This Session already exists");
                        ErrRoom.setText("This Session already exists");
                        verified=true;
                    }
                }

                if(!verified)
                {
                    //adding the session if possible
                    request = "INSERT INTO `session` (`ID_session`, `Date`, `ID_movie`, `id_cinema`, `Discount`, `Price`, `ID_room`) VALUES (NULL, '"+txtDate.getText()+"', '"+id_movie+"', '"+id_venue+"', '"+txtDiscount.getText()+"', '"+txtPrice.getText()+"', '"+comboRoom.getSelectionModel().getSelectedItem()+"');";

                    stat.executeUpdate(request);

                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                    HomeController hc = fxmlLoader.getController();
                    hc.Initialisation(account);
                    hc.AddSession();
                    Scene scene = new Scene(root);
                    lstage.setScene(scene);
                    lstage.show();

                }

            } catch (Exception e1) {
                ErrDate.setText("Put Date -> YYYY-MM-DD H:M:S");
            }
        }
    }

    @FXML
    void AddExit(ActionEvent event) {
        //same as before, but instead of staying on the same page, it goes back to the home page
        boolean error=false;

        ErrDate.setText("");
        ErrMovie.setText("");
        ErrDiscount.setText("");
        ErrPrice.setText("");
        ErrRoom.setText("");
        ErrVenue.setText("");

        if(txtDate.getText().length()==0)
        {
            ErrDate.setText("Put a Date");
            error=true;
        }
        if(txtPrice.getText().length()==0)
        {
            ErrPrice.setText("Put a Date");
            error=true;
        }
        if(txtDiscount.getText().length()==0)
        {
            ErrDiscount.setText("Put a Discount");
            error=true;
        }
        if(!comboMovie.isDisable())
        {
            ErrMovie.setText("Put a Movie");
            error=true;
        }
        if(!comboVenue.isDisable())
        {
            ErrVenue.setText("Put a Venue");
            error=true;
        }
        if(!comboRoom.isDisable())
        {
            ErrRoom.setText("Put a Room");
            error=true;
        }

        if(!error)
        {

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");

                while (rs.next())
                {
                    if(rs.getString("Name").equals(comboMovie.getSelectionModel().getSelectedItem())){
                        id_movie=rs.getInt("ID_movie");
                    }
                }

            }catch (Exception e1){
                System.out.println(e1.getMessage());
            }

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM `cinema`");

                while (rs.next())
                {
                    if(rs.getString("name").equals(comboVenue.getSelectionModel().getSelectedItem())){
                        id_venue=rs.getInt("Id_cinema");
                    }
                }

            }catch (Exception e1){
                System.out.println(e1.getMessage());
            }

            String request;

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                boolean verified=false;

                ResultSet rs = stat.executeQuery("SELECT * FROM `session`");

                while (rs.next())
                {
                    if(txtDate.getText().equals(rs.getString("Date")) && id_venue == rs.getInt("id_cinema") && comboRoom.getSelectionModel().getSelectedItem().equals(rs.getString("ID_room")))
                    {
                        ErrDate.setText("This Session already exists");
                        ErrVenue.setText("This Session already exists");
                        ErrRoom.setText("This Session already exists");
                        verified=true;
                    }
                }

                if(!verified)
                {
                    request = "INSERT INTO `session` (`ID_session`, `Date`, `ID_movie`, `id_cinema`, `Discount`, `Price`, `ID_room` , `seat_1`, `seat_2`, `seat_3`, `seat_4`, `seat_5`, `seat_6`, `seat_7`, `seat_8`, `seat_9`, `seat_10`, `seat_11`, `seat_12`, `seat_13`, `seat_14`, `seat_15`, `seat_16`, `seat_17`, `seat_18`, `seat_19`, `seat_20`, `seat_21`, `seat_22`, `seat_23`, `seat_24`, `seat_25`, `seat_26`, `seat_27`, `seat_28`, `seat_29`, `seat_30`) VALUES (NULL, '"+txtDate.getText()+"', '"+id_movie+"', '"+id_venue+"', '"+txtDiscount.getText()+"', '"+txtPrice.getText()+"', '"+comboRoom.getSelectionModel().getSelectedItem()+"','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');";
                    stat.executeUpdate(request);

                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                    HomeController hc = fxmlLoader.getController();
                    hc.Initialisation(account);
                    Scene scene = new Scene(root);
                    lstage.setScene(scene);
                    lstage.show();

                }

            } catch (Exception e1) {
                ErrDate.setText("Put Date -> YYYY-MM-DD H:M:S");
            }
        }
    }
}
