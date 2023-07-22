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

import java.io.IOException;
import java.sql.*;

public class UpdateSessionController {

    @FXML
    private ComboBox<String> comboMovie;

    @FXML
    private ComboBox<String> comboRoom;

    @FXML
    private ComboBox<String> comboVenue;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtPrice;

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

    private int account;
    private int id_session;
    private int id_movie;
    private int id_venue;

    public void setSession(String movie, String cinema, String ID_room, String Discount, String Price, String Date, int account, int session)
    {
        //setting every good information to set the session we want
        this.account = account;
        this.id_session=session;
        ObservableList<String> Movie = FXCollections.observableArrayList();
        ObservableList<String> Cinema = FXCollections.observableArrayList();
        ObservableList<String> room = FXCollections.observableArrayList();

        Movie.add(movie);
        Cinema.add(cinema);
        room.add(ID_room);


        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM movie");

            while (rs.next()) {
                if(!rs.getString("Name").equals(movie))
                {
                    String movie_name = rs.getString("Name");
                    Movie.add(movie_name);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM cinema");

            while (rs.next()) {
                if(!rs.getString("name").equals(cinema))
                {
                    String cinema_name = rs.getString("name");
                    Cinema.add(cinema_name);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM room");

            while (rs.next()) {
                if(!rs.getString("ID_room").equals(ID_room))
                {
                    String room_id = rs.getString("ID_room");
                    room.add(room_id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //
        comboMovie.setItems(Movie);
        comboVenue.setItems(Cinema);
        comboRoom.setItems(room);

        comboMovie.setPromptText(movie);
        comboVenue.setPromptText(cinema);
        comboRoom.setPromptText(ID_room);

        txtPrice.setText(Price);
        txtDiscount.setText(Discount);
        txtDate.setText(Date);
    }

    @FXML
    public void setMovie() {
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
        boolean error=false;
        //shows errors if ther is some
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
                        //shows message if the session already exists
                        ErrDate.setText("This Session already exists");
                        ErrVenue.setText("This Session already exists");
                        ErrRoom.setText("This Session already exists");
                        verified=true;
                    }
                }
                //if everything is good we add it to the database
                if(!verified)
                {
                    request = "INSERT INTO `session` (`ID_session`, `Date`, `ID_movie`, `id_cinema`, `Discount`, `Price`, `ID_room`) VALUES (NULL, '"+txtDate.getText()+"', '"+id_movie+"', '"+id_venue+"', '"+txtDiscount.getText()+"', '"+txtPrice.getText()+"', '"+comboRoom.getSelectionModel().getSelectedItem()+"');";

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

    @FXML
    public void DeleteSession(ActionEvent event) throws IOException {
        String request;
        //delete session button that search a session by its id
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();

            request = "DELETE FROM `session` WHERE ID_session = '" + id_session + "'";
            stat.executeUpdate(request);
        }
        catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
        //sends to the home page
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
        Parent root = fxmlLoader.load();
        Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        HomeController hc = fxmlLoader.getController();
        hc.Initialisation(account);
        hc.UpdateSession();
        Scene scene = new Scene(root);
        lstage.setScene(scene);
        lstage.show();
    }

}
