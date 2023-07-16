package com.example.movie_ticket_booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SalesDiscountController {

    @FXML
    private ImageView imgPoster;

    @FXML
    private Label txtError;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtMovie;

    @FXML
    private Label txtRoom;

    @FXML
    private Label txtVenue;

    @FXML
    private TextField txtdiscount;

    private int ID_session;

    private int account;

    @FXML
    void submit(ActionEvent event) {
        String request;

        if(Integer.parseInt(txtdiscount.getText())<=100 && Integer.parseInt(txtdiscount.getText())>=0)
        {
            txtError.setText("");
            try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();

                request = "UPDATE `session` SET `discount`='"+txtdiscount.getText()+"' WHERE ID_session='"+ID_session+"'";

                stat.executeUpdate(request);

                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
                Parent root = fxmlLoader.load();
                Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                HomeController hc = fxmlLoader.getController();
                hc.Initialisation(account);
                hc.DiscountOffers();
                Scene scene = new Scene(root);
                lstage.setScene(scene);
                lstage.show();

            }catch (Exception e1){
                System.out.println(e1.getMessage());
            }
        }
        else
        {
            txtError.setText("Only between 0 and 100");
        }

    }

    public void setDiscountInfo(String name, String cinema, String date, String room, String poster, String discount, int ID, int account){
        txtMovie.setText(name);
        txtDate.setText(date);
        txtVenue.setText(cinema);
        txtRoom.setText(room);
        imgPoster.setImage(new Image(poster));
        txtdiscount.setText(discount);
        ID_session = ID;
        this.account=account;
    }

}
