package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MovieController {
    @FXML
    private Label Year;

    @FXML
    private Label Genre;

    @FXML
    private Label Name;

    @FXML
    private ImageView poster;

    public int Id_movie_on_click;
    private static final double BRIGHTNESS_DELTA = -0.4;
    private int account;

    public void SetMovie(String post, String Gen, String Nam, String Yea, int acc, String review) throws SQLException {
        //function that sets a movie with every information needed
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
        //modifies the color of a movie
        ColorAdjust colorAdjust = new ColorAdjust();
        poster.setEffect(colorAdjust);
        //displaying the information under the movie
        this.account=acc;
        poster.setImage(new Image(post));
        Name.setText(Nam);
        Genre.setText(Gen);
        Year.setText(Yea + "  |  "+ "Review: " + review + " ⭐");
        //modifying the brightness of the poster when the mouse is on it
        poster.setOnMouseEntered(mouseEvent -> colorAdjust.setBrightness(BRIGHTNESS_DELTA));
        poster.setOnMouseExited(event -> colorAdjust.setBrightness(0));
        //action when we click on a movie
        poster.setOnMouseClicked(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            HomeController hc = fxmlLoader.getController();
            hc.Initialisation(account);
            try {
                hc.Initialisation2(Name.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            lstage.setScene(scene);
            lstage.show();

            try {
                String sql = "SELECT Id_movie FROM `movie` WHERE name = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, Nam);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    //searching the movie by its id
                    Id_movie_on_click = rs.getInt("Id_movie");
                } else {
                    System.out.println("Movie not found.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
