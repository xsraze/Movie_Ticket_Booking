package com.example.movie_ticket_booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

    public void SetMovie(String post, String Gen, String Nam, String Yea, int acc, String review) throws IOException, SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        ColorAdjust colorAdjust = new ColorAdjust();
        poster.setEffect(colorAdjust);

        this.account=acc;
        poster.setImage(new Image(post));
        Name.setText(Nam);
        Genre.setText(Gen);
        Year.setText(Yea + "  |  "+ "Review: " + review + " ⭐");

        poster.setOnMouseEntered(mouseEvent -> {
            colorAdjust.setBrightness(BRIGHTNESS_DELTA);
        });
        poster.setOnMouseExited(event -> {
            colorAdjust.setBrightness(0);
        });
        poster.setOnMouseClicked(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
            Parent root = null;
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
                    int id = rs.getInt("Id_movie");
                    Id_movie_on_click = id;
                } else {
                    System.out.println("Film non trouvé.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
