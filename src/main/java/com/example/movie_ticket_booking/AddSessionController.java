package com.example.movie_ticket_booking;

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
    private ComboBox<?> comboRoom;

    @FXML
    private ComboBox<?> comboVenue;

    @FXML
    private ComboBox<?> comboMovie;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtPrice;

    private int account;

    public void setAccount(int acc) {
        account=acc;
    }

    public void SetCombo(ObservableList<String> venue, ObservableList<String> room, ObservableList<String> movie)
    {

    }


    @FXML
    void AddAgain(ActionEvent event) {
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
            String request;

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                boolean verified=false;

                ResultSet rs = stat.executeQuery("SELECT * FROM `session`");

                while (rs.next())
                {
                    if(txtDate.getText().equals(rs.getString("Date")) && comboVenue.getSelectionModel().getSelectedItem().equals(rs.getString("id_cinema")) && comboRoom.getSelectionModel().getSelectedItem().equals(rs.getString("ID_room")))
                    {
                        ErrDate.setText("This Session already exists");
                        ErrVenue.setText("This Session already exists");
                        ErrRoom.setText("This Session already exists");
                        verified=true;
                    }
                }

                if(!verified)
                {
                    request = "INSERT INTO `session` (`ID_session`, `Date`, `ID_movie`, `id_cinema`, `Discount`, `Price`, `ID_room`) VALUES (NULL, '"+txtDate.getText()+"', '"+comboMovie.getSelectionModel().getSelectedItem()+"', '"+comboVenue.getSelectionModel().getSelectedItem()+"', '"+txtDiscount.getText()+"', '"+txtPrice.getText()+"', '"+comboRoom.getSelectionModel().getSelectedItem()+"');";

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
                System.out.println(e1.getMessage());
            }
        }
    }

    @FXML
    void AddExit(ActionEvent event) {
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
            String request;

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                boolean verified=false;

                ResultSet rs = stat.executeQuery("SELECT * FROM `session`");

                while (rs.next())
                {
                    if(txtDate.getText().equals(rs.getString("Date")) && comboVenue.getSelectionModel().getSelectedItem().equals(rs.getString("id_cinema")) && comboRoom.getSelectionModel().getSelectedItem().equals(rs.getString("ID_room")))
                    {
                        ErrDate.setText("This Session already exists");
                        ErrVenue.setText("This Session already exists");
                        ErrRoom.setText("This Session already exists");
                        verified=true;
                    }
                }

                if(!verified)
                {
                    request = "INSERT INTO `session` (`ID_session`, `Date`, `ID_movie`, `id_cinema`, `Discount`, `Price`, `ID_room`) VALUES (NULL, '"+txtDate.getText()+"', '"+comboMovie.getSelectionModel().getSelectedItem()+"', '"+comboVenue.getSelectionModel().getSelectedItem()+"', '"+txtDiscount.getText()+"', '"+txtPrice.getText()+"', '"+comboRoom.getSelectionModel().getSelectedItem()+"');";

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
                System.out.println(e1.getMessage());
            }
        }
    }
}
