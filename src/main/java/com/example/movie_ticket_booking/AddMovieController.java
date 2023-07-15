package com.example.movie_ticket_booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddMovieController {
    @FXML
    private TextArea txtResume;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtGenre;

    @FXML
    private TextField txtReview;

    @FXML
    private TextField txtPoster;

    @FXML
    private TextField txtRealisator;

    @FXML
    private Label ErrName;

    @FXML
    private Label ErrRealisator;

    @FXML
    private Label ErrYear;

    @FXML
    private Label ErrGenre;

    @FXML
    private Label ErrPoster;

    @FXML
    private Label ErrReview;

    @FXML
    private Label ErrResume;

    private int Id;

    public void setId(int id)
    {
        Id=id;
    }

    @FXML
    void Add(ActionEvent event) {
        boolean error=false;

        ErrYear.setText("");
        ErrName.setText("");
        ErrRealisator.setText("");
        ErrResume.setText("");
        ErrReview.setText("");
        ErrGenre.setText("");
        ErrPoster.setText("");

        if(txtYear.getText().length()==0)
        {
            ErrYear.setText("Put an Year");
            error=true;
        }
        if(txtName.getText().length()==0)
        {
            ErrName.setText("Put a Name");
            error=true;
        }
        if(txtGenre.getText().length()==0)
        {
            ErrGenre.setText("Put a Genre");
            error=true;
        }
        if(txtPoster.getText().length()==0)
        {
            ErrPoster.setText("Put a link to a poster");
            error=true;
        }
        if(txtResume.getText().length()==0)
        {
            ErrResume.setText("Put a Resume");
            error=true;
        }
        if(txtRealisator.getText().length()==0)
        {
            ErrRealisator.setText("Put a Realisator");
            error=true;
        }
        if(txtReview.getText().length()==0)
        {
            ErrReview.setText("Put a Review");
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
                    if(txtName.getText().equals(rs.getString("Name")))
                    {
                        ErrName.setText("This Name Movie already exists");
                        verified=true;
                    }
                        if(txtPoster.getText().equals(rs.getString("poster")))
                    {
                        ErrPoster.setText("This poster is already used");
                        verified=true;
                    }
                    if(txtResume.getText().equals(rs.getString("Resume")))
                    {
                        ErrResume.setText("This Resume is already use");
                        verified=true;
                    }
                }

                if(!verified)
                {
                    request = "INSERT INTO `movie` (`ID_movie`, `Name`, `poster`, `Resume`, `Genre`, `Author`, `Year`, `Review`) VALUES (NULL, '"+txtName.getText()+"', '"+txtPoster.getText()+"', '"+txtResume.getText()+"', '"+txtGenre.getText()+"', '"+txtRealisator.getText()+"', '"+txtYear.getText()+"', '"+txtReview.getText()+"');";

                    stat.executeUpdate(request);

                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                    HomeController hc = fxmlLoader.getController();
                    hc.Initialisation(Id);
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
