package com.example.movie_ticket_booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HomeController {

    private int account;
    @FXML
    private BorderPane bpane;
    @FXML
    private ComboBox<String> combo_date;

    @FXML
    private ComboBox<String>  combo_movie;

    @FXML
    private ComboBox<String> combo_seat;

    @FXML
    private ComboBox<String> combo_venue;

    @FXML
    private Button login_btn;

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
    private Label txtsaleSession;
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

    @FXML
    private Label txtAddSession;

    @FXML
    private Label txtUpdateSession;

    public void Initialisation(int acc){
        account=acc;
        int col = 0;
        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController mc = fxmlLoader.getController();
                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), rs.getString("Review"));

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
                            txtAddSession.setText("Add Session");
                            txtUpdateSession.setText("Update Session");
                            txtupdate.setText("Update Movies");
                            txtdiscount.setText("Discount Offers");
                            txtprofile.setText("Update profiles");
                            txtsaleCinema.setText("Sales Analyzed by Cinema");
                            txtsaleMovie.setText("Sales Analyzed by Movie");
                            txtsaleSession.setText("Sales Analyzed by Session");
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
            txtUpdateSession.setText("");
            txtAddSession.setText("");
            txtupdate.setText("");
            txtdiscount.setText("");
            txtprofile.setText("");
            txtsaleCinema.setText("");
            txtsaleMovie.setText("");
            txtsaleSession.setText("");
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
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int col = 0;
        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY Review DESC");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController mc = fxmlLoader.getController();
                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), rs.getString("Review"));

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
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

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
                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), rs.getString("Review"));

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
    void Genre() {
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

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
                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), rs.getString("Review"));

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
    void Signin() throws IOException {
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
    void profile() throws IOException {
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
    void DiscountOffers() {
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM session JOIN movie WHERE session.ID_movie=movie.ID_movie");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("SalesDiscount.fxml"));
                VBox SalesDiscount = fxmlLoader.load();
                SalesDiscountController sdc = fxmlLoader.getController();
                sdc.setDiscountInfo(rs.getString("movie.name"), rs.getString("session.id_cinema"), rs.getString("session.date"), rs.getString("session.ID_room"), rs.getString("movie.poster"), rs.getString("session.discount"), rs.getInt("session.ID_session"), account);
                System.out.println("Probleme");
                ++line;

                MovieContainer.add(SalesDiscount, 0, line);
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    @FXML
    void MovieAnalyzed() throws IOException {

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
    void SalesCinema() {
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT cinema.name, SUM(reservation.Nb_tickets) AS NombrePlacesVendues, SUM(reservation.FinalPrice) AS RevenusGeneres FROM cinema JOIN session ON cinema.Id_cinema = session.Id_cinema JOIN reservation ON session.ID_session = reservation.ID_session GROUP BY cinema.name");

            while (rs.next())
            {
                int price=rs.getInt("RevenusGeneres");
                int seat=rs.getInt("NombrePlacesVendues");

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("SalesCinema.fxml"));
                VBox SalesCinema = fxmlLoader.load();
                SalesCinemaController scc = fxmlLoader.getController();
                scc.setSalesMovie(rs.getString("name"), price, seat);

                ++line;

                MovieContainer.add(SalesCinema, 0, line);
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT cinema.name FROM cinema WHERE NOT EXISTS ( SELECT 1 FROM session INNER JOIN reservation ON session.ID_session = reservation.ID_session WHERE cinema.Id_cinema = session.Id_cinema )");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("SalesCinema.fxml"));
                VBox SalesCinema = fxmlLoader.load();
                SalesCinemaController scc = fxmlLoader.getController();
                scc.setSalesMovie(rs.getString("name"), 0, 0);

                ++line;

                MovieContainer.add(SalesCinema, 0, line);
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    @FXML
    void SalesMovie() {
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int line = 1;

        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT movie.ID_movie, movie.Name, movie.Author, movie.Year, movie.Genre, movie.poster, SUM(FinalPrice) AS SommePrice, SUM(Nb_tickets) AS SommeTicket FROM reservation JOIN session ON reservation.ID_session=session.ID_session JOIN cinema ON cinema.Id_cinema=session.Id_cinema JOIN movie ON movie.ID_movie=session.ID_movie GROUP BY movie.Name");

            while(rs.next())
            {
                int price=rs.getInt("SommePrice");
                int seat=rs.getInt("SommeTicket");

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("SalesMovie.fxml"));
                VBox SalesMovieBox = fxmlLoader.load();
                SalesMovieController smc = fxmlLoader.getController();
                smc.setSalesMovie(rs.getString("movie.Name"), rs.getString("movie.Author"), rs.getInt("movie.Year"), rs.getString("movie.Genre"),seat,price,rs.getString("movie.poster"));
                ++line;

                MovieContainer.add(SalesMovieBox, 0, line);
            }

            con.close();
        }catch (Exception e)
        {
            System.out.println("mouais");
        }

        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM movie WHERE movie.ID_movie NOT IN ( SELECT DISTINCT session.ID_movie FROM session INNER JOIN reservation ON session.ID_session = reservation.ID_session )");

            while(rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("SalesMovie.fxml"));
                VBox SalesMovieBox = fxmlLoader.load();
                SalesMovieController smc = fxmlLoader.getController();
                smc.setSalesMovie(rs.getString("movie.Name"), rs.getString("movie.Author"), rs.getInt("movie.Year"), rs.getString("Movie.Genre"),0,0,rs.getString("movie.poster"));

                ++line;

                MovieContainer.add(SalesMovieBox, 0, line);
            }

            con.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void SalesSession() {
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int line = 1;

        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT reservation.ID_session, movie.Name, cinema.name, session.Date, session.ID_room, movie.poster, SUM(FinalPrice) AS SommePrice, SUM(Nb_tickets) AS SommeTicket FROM reservation JOIN session ON reservation.ID_session=session.ID_session JOIN cinema ON cinema.Id_cinema=session.Id_cinema JOIN movie ON movie.ID_movie=session.ID_movie GROUP BY movie.Name, cinema.name, session.Date, session.ID_room, movie.poster");

            while(rs.next())
            {
                int price=rs.getInt("SommePrice");
                int seat=rs.getInt("SommeTicket");

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("SalesSession.fxml"));
                VBox SalesSessionBox = fxmlLoader.load();
                SalesSessionController ssc = fxmlLoader.getController();
                ssc.SetSaleSession(rs.getString("movie.Name"), rs.getString("cinema.name"), rs.getString("session.Date"), rs.getString("session.ID_room"),seat,price,rs.getString("movie.poster"));

                ++line;

                MovieContainer.add(SalesSessionBox, 0, line);
            }

            con.close();
        }catch (Exception e)
        {
            System.out.println("mouais");
        }

        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT session.ID_session, movie.Name, cinema.name, session.Date, session.ID_room, movie.poster FROM session LEFT JOIN reservation ON session.ID_session = reservation.ID_session JOIN cinema ON cinema.Id_cinema=session.Id_cinema JOIN movie ON movie.ID_movie=session.ID_movie WHERE reservation.ID_session IS NULL");

            while(rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("SalesSession.fxml"));
                VBox SalesSessionBox = fxmlLoader.load();
                SalesSessionController ssc = fxmlLoader.getController();
                ssc.SetSaleSession(rs.getString("movie.Name"), rs.getString("cinema.name"), rs.getString("session.Date"), rs.getString("session.ID_room"),0,0,rs.getString("movie.poster"));

                ++line;

                MovieContainer.add(SalesSessionBox, 0, line);
            }

            con.close();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
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

    @FXML
    void UpdateSession() {

        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie JOIN room ON room.ID_room=session.ID_room JOIN cinema ON cinema.Id_cinema=session.Id_cinema");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("UpdateSession.fxml"));
                VBox UpdateSession = fxmlLoader.load();
                UpdateSessionController usc = fxmlLoader.getController();
                usc.setSession(rs.getString("Name"), rs.getString("cinema.name"), rs.getString("ID_room"), rs.getString("Discount"), rs.getString("Price"), rs.getString("Date"), account, rs.getInt("ID_session"));

                ++line;

                MovieContainer.add(UpdateSession, 0, line);
            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    @FXML
    void AddSession() throws IOException {

        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<String> items2 = FXCollections.observableArrayList();
        ObservableList<String> items3 = FXCollections.observableArrayList();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddSession.fxml"));
        AnchorPane addsession = fxmlLoader.load();
        AddSessionController asc = fxmlLoader.getController();
        asc.setAccount(account);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM movie");

            while (rs.next()) {
                String movie = rs.getString("Name");
                items.add(movie);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM cinema");

            while (rs.next()) {
                String cinema = rs.getString("name");
                items2.add(cinema);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM room");

            while (rs.next()) {
                String room = rs.getString("ID_room");
                items3.add(room);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        asc.SetCombo(items2, items3, items);
        MovieContainer.add(addsession, 0, 1);
    }

    @FXML
    public void search()
    {
        if(searchbar.getText().length()!=0)
        {
            MovieContainer.getChildren().clear();
            MovieContainer.getColumnConstraints().clear();
            MovieContainer.getRowConstraints().clear();

            int col = 0;
            int line = 1;
            boolean find=false;

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM movie");

                while (rs.next())
                {
                    if(searchbar.getText().equalsIgnoreCase(rs.getString("Name")) || searchbar.getText().equalsIgnoreCase(rs.getString("Genre")) || searchbar.getText().equalsIgnoreCase(rs.getString("Year")))
                    {
                        find = true;
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                        VBox movieBox = fxmlLoader.load();
                        MovieController mc = fxmlLoader.getController();
                        mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), rs.getString("Review"));

                        if(col == 3)
                        {
                            col =0;
                            ++line;
                        }

                        MovieContainer.add(movieBox, col++, line);
                        GridPane.setMargin(movieBox, new Insets(10));
                    }
                }
                con.close();
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

            if(!find)
            {
                searchbar.clear();
                searchbar.setPromptText("Nothing was found...");

                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

                    Statement stat = con.createStatement();
                    ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");

                    while (rs.next())
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                        VBox movieBox = fxmlLoader.load();
                        MovieController mc = fxmlLoader.getController();
                        mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), rs.getString("Review"));

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
        }
    }

    @FXML
    public void quickBook()
    {

    }
}
