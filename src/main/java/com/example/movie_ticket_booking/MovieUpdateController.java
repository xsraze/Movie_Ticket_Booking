package com.example.movie_ticket_booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MovieUpdateController {
    @FXML
    private TextField txtGenre;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPoster;

    @FXML
    private TextField txtRealisator;

    @FXML
    private TextArea txtResume;

    @FXML
    private TextField txtReview;

    @FXML
    private TextField txtYear;
    private int ID;
    private int ID_admin;

    @FXML
    void DeleteMovie(ActionEvent event) throws IOException {
        //delete movie button by its id
        String request;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();

            request = "DELETE FROM `movie` WHERE ID_movie = '" + ID + "'";
            stat.executeUpdate(request);
        }
        catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
        Parent root = fxmlLoader.load();
        Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        HomeController hc = fxmlLoader.getController();
        hc.Initialisation(ID_admin);
        hc.UpdateMovie();
        Scene scene = new Scene(root);
        lstage.setScene(scene);
        lstage.show();
    }

    @FXML
    void UpdateMovie(ActionEvent event) {
        //modifying a movie and checking if everything is well filled
        boolean error= txtResume.getText().length() == 0;
        //checking every possible error
        if(txtName.getText().length()==0)
        {
            error=true;
        }
        if(txtReview.getText().length()==0)
        {
            error=true;
        }
        if(txtPoster.getText().length()==0)
        {
            error=true;
        }
        if(txtGenre.getText().length()==0)
        {
            error=true;
        }
        if(txtRealisator.getText().length()==0)
        {
            error=true;
        }
        if(txtYear.getText().length()==0)
        {
            error=true;
        }
        if(Float.parseFloat(txtReview.getText())>5 || Float.parseFloat(txtReview.getText())<0)
        {
            error=true;
        }

        if(!error)
        {
            String request;

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                boolean verified=false;

                ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");

                while (rs.next())
                {
                    if(txtName.getText().equals(rs.getString("Name")) && ID!=rs.getInt("ID_movie"))
                    {
                        verified=true;
                    }
                    if(txtPoster.getText().equals(rs.getString("poster")) && ID != rs.getInt("ID_movie"))
                    {
                        verified=true;
                    }
                    if(txtResume.getText().equals(rs.getString("Resume"))  && ID != rs.getInt("ID_movie"))
                    {
                        verified=true;
                    }
                }

                if(!verified)
                {
                    //modifying teh movie if there is no error
                    request ="UPDATE `movie` SET `Name`='"+txtName.getText()+"',`poster`='"+txtPoster.getText()+"',`Resume`='"+txtResume.getText()+"',`Genre`='"+txtGenre.getText()+"',`Author`='"+txtRealisator.getText()+"',`Year`='"+txtYear.getText()+"',`Review`='"+txtReview.getText()+"' WHERE ID_movie = '"+ID+"'";


                    stat.executeUpdate(request);

                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                    HomeController hc = fxmlLoader.getController();
                    hc.Initialisation(ID_admin);
                    hc.UpdateMovie();
                    Scene scene = new Scene(root);
                    lstage.setScene(scene);
                    lstage.show();
                }

            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

    public void setMovie(String name, String Realisator, String poster, int year, String genre, String review, String Resume, int id, int id_admin)
    {
        //setting the movie with the good information
        ID_admin=id_admin;
        ID=id;
        txtName.setText(name);
        txtGenre.setText(genre);
        txtRealisator.setText(Realisator);
        txtPoster.setText(poster);
        txtYear.setText(String.valueOf(year));
        txtReview.setText(review);
        txtResume.setText(Resume);

    }
}
