package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeController {

    private int account;
    @FXML
    private BorderPane bpane;
    @FXML
    private ComboBox<?> combo_date;

    @FXML
    private ComboBox<?> combo_movie;

    @FXML
    private ComboBox<?> combo_seat;

    @FXML
    private ComboBox<?> combo_venue;

    @FXML
    private Button login_btn;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView logo1;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button search_qb;

    @FXML
    private TextField searchbar;

    @FXML
    private Button signin_btn;
    @FXML
    private GridPane MovieContainer;
    @FXML
    private Label txtadmin;
    @FXML
    private Label txtsaleMovie;
    @FXML
    private Label txtanalyzed;

    @FXML
    private Label txtdiscount;

    @FXML
    private Label txtprofile;

    @FXML
    private Label txtsaleCinema;

    @FXML
    private Label txtupdate;

    public void Initialisation(int acc){
        account=acc;
        int col = 0;
        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");
            System.out.println("Hello");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController mc = fxmlLoader.getController();
                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"));

                if(col == 3)
                {
                    col =0;
                    ++line;
                }

                MovieContainer.add(movieBox, col++, line);
                GridPane.setMargin(movieBox, new Insets(10));
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        if(account!=0)
        {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM `users`");

                while (rs.next())
                {
                    if(Integer.parseInt(rs.getString("ID_user")) == account)
                    {
                        signin_btn.setText("Hello "+rs.getString("username")+"!");

                        if(rs.getString("Type").equals("admin"))
                        {
                            txtadmin.setText("Admin");
                            txtanalyzed.setText("Add Movies");
                            txtupdate.setText("Update Movies");
                            txtdiscount.setText("Discount Offers");
                            txtprofile.setText("Update profiles");
                            txtsaleCinema.setText("Sales Analyzed by Cinema");
                            txtsaleMovie.setText("Sales Analyzed by Movie");
                        }
                    }

                    login_btn.setText("Log out");
                }
                con.close();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }

        else{
            txtadmin.setText("");
            txtanalyzed.setText("");
            txtupdate.setText("");
            txtdiscount.setText("");
            txtprofile.setText("");
            txtsaleCinema.setText("");
            txtsaleMovie.setText("");
        }
    }

    @FXML
    void Home(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
        Parent root = fxmlLoader.load();
        Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        HomeController hc = fxmlLoader.getController();
        hc.Initialisation(account);
        Scene scene = new Scene(root);
        lstage.setScene(scene);
        lstage.show();
    }

    @FXML
    void Name() {
        int col = 0;
        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY Name ASC");
            System.out.println("Hello");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController mc = fxmlLoader.getController();
                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"));

                if(col == 3)
                {
                    col =0;
                    ++line;
                }

                MovieContainer.add(movieBox, col++, line);
                GridPane.setMargin(movieBox, new Insets(10));
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    @FXML
    void Year() {
        int col = 0;
        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY Year ASC");
            System.out.println("Hello");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController mc = fxmlLoader.getController();
                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"));

                if(col == 3)
                {
                    col =0;
                    ++line;
                }

                MovieContainer.add(movieBox, col++, line);
                GridPane.setMargin(movieBox, new Insets(10));
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    @FXML
    void Genre(MouseEvent event) {
        int col = 0;
        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY Genre ASC");
            System.out.println("Hello");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController mc = fxmlLoader.getController();
                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"));

                if(col == 3)
                {
                    col =0;
                    ++line;
                }

                MovieContainer.add(movieBox, col++, line);
                GridPane.setMargin(movieBox, new Insets(10));
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    @FXML
    void Login(MouseEvent event) throws IOException {

        if(login_btn.getText().equals("Log in"))
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Login.fxml"));
            AnchorPane Film = fxmlLoader.load();
            LoginController lc = fxmlLoader.getController();
            bpane.setCenter(Film);
        }

        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
            Parent root = fxmlLoader.load();
            Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            HomeController hc = fxmlLoader.getController();
            hc.Initialisation(0);
            Scene scene = new Scene(root);
            lstage.setScene(scene);
            lstage.show();
        }
    }

    @FXML
    void Signin(MouseEvent event) throws IOException {
        if(signin_btn.getText().equals("Sign in"))
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Registration.fxml"));
            AnchorPane registration = fxmlLoader.load();
            RegistrationController rc = fxmlLoader.getController();
            bpane.setCenter(registration);
        }
    }

    @FXML
    void profile(MouseEvent event) throws IOException {
        if(login_btn.getText().equals("Log in"))
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Login.fxml"));
            AnchorPane login = fxmlLoader.load();
            LoginController lc = fxmlLoader.getController();
            bpane.setCenter(login);
        }

        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Info.fxml"));
            AnchorPane info = fxmlLoader.load();
            InfoController ic = fxmlLoader.getController();
            ic.SetInfo(account);
            bpane.setCenter(info);
        }
    }

    @FXML
    void DiscountOffers(MouseEvent event) {

    }

    @FXML
    void MovieAnalyzed(MouseEvent event) throws IOException {

        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddMovie.fxml"));
        AnchorPane Addmovie = fxmlLoader.load();
        AddMovieController ac = fxmlLoader.getController();
        ac.setId(account);

        MovieContainer.add(Addmovie, 0, 1);
    }

    @FXML
    void SalesCinema(MouseEvent event) {
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieUpdate.fxml"));
                VBox MovieUpdateBox = fxmlLoader.load();
                MovieUpdateController uc = fxmlLoader.getController();
                uc.setMovie(rs.getString("Name"), rs.getString("Author"), rs.getString("poster"), rs.getInt("Year"), rs.getString("Genre"), rs.getString("Review"), rs.getString("Resume"), rs.getInt("ID_movie"), account);

                ++line;

                MovieContainer.add(MovieUpdateBox, 0, line);
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    @FXML
    void SalesMovie(MouseEvent event) {
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("SalesMovie.fxml"));
                VBox SalesMovieBox = fxmlLoader.load();
                SalesMovieController smc = fxmlLoader.getController();
                smc.setSalesMovie(rs.getString("movie.Name"), rs.getString("movie.Author"), rs.getInt("movie.Year"), rs.getString("movie.Genre"));

                ++line;

                MovieContainer.add(SalesMovieBox, 0, line);
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    @FXML
    void UpdateMovie() {

        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieUpdate.fxml"));
                VBox MovieUpdateBox = fxmlLoader.load();
                MovieUpdateController uc = fxmlLoader.getController();
                uc.setMovie(rs.getString("Name"), rs.getString("Author"), rs.getString("poster"), rs.getInt("Year"), rs.getString("Genre"), rs.getString("Review"), rs.getString("Resume"), rs.getInt("ID_movie"), account);

                ++line;

                MovieContainer.add(MovieUpdateBox, 0, line);
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    @FXML
    void UpdateProfiles() {

        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `users`");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("User.fxml"));
                VBox UserBox = fxmlLoader.load();
                UserController uc = fxmlLoader.getController();
                uc.setUser(rs.getString("Name"), rs.getString("LastName"), rs.getString("email"), rs.getString("phone"), rs.getString("username"), rs.getString("password"), rs.getString("Type"), rs.getInt("ID_user"), account);

                ++line;

                MovieContainer.add(UserBox, 0, line);
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }
}
