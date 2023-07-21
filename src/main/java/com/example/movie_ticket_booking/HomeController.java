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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class HomeController {

    private int account;
    @FXML
    private BorderPane bpane;

    @FXML
    private ComboBox<String> combo_date;

    @FXML
    private ComboBox<String> combo_movie;

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

    public int price_tick;
    public int discount;
    private String poster;
    private String name;
    private String email;
    private int id_room;

    @FXML
    private Label txtupdate;

    @FXML
    private Label txtAddSession;

    @FXML
    private Label txtUpdateSession;


    public void Initialisation(int acc){
        account=acc;

        combo_venue.setDisable(true);
        combo_date.setDisable(true);
        combo_seat.setDisable(true);

        ObservableList<String> movie = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM movie");

            while (rs.next()) {
                    String movie_name = rs.getString("Name");
                    movie.add(movie_name);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        combo_movie.setItems(movie);

        int col = 0;
        int line = 1;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");

            while (rs.next()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController mc = fxmlLoader.getController();
                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), account, rs.getString("Review"));

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

        if (account != 0) {
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

                            txtadmin.setDisable(false);
                            txtanalyzed.setDisable(false);
                            txtAddSession.setDisable(false);
                            txtUpdateSession.setDisable(false);
                            txtupdate.setDisable(false);
                            txtdiscount.setDisable(false);
                            txtprofile.setDisable(false);
                            txtsaleCinema.setDisable(false);
                            txtsaleMovie.setDisable(false);
                            txtsaleSession.setDisable(false);
                        }

                        else
                        {
                            txtadmin.setText("");
                            txtanalyzed.setText("");
                            txtAddSession.setText("");
                            txtUpdateSession.setText("");
                            txtupdate.setText("");
                            txtdiscount.setText("");
                            txtprofile.setText("");
                            txtsaleCinema.setText("");
                            txtsaleMovie.setText("");
                            txtsaleSession.setText("");

                            txtadmin.setDisable(true);
                            txtanalyzed.setDisable(true);
                            txtAddSession.setDisable(true);
                            txtUpdateSession.setDisable(true);
                            txtupdate.setDisable(true);
                            txtdiscount.setDisable(true);
                            txtprofile.setDisable(true);
                            txtsaleCinema.setDisable(true);
                            txtsaleMovie.setDisable(true);
                            txtsaleSession.setDisable(true);
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
            txtAddSession.setText("");
            txtUpdateSession.setText("");
            txtupdate.setText("");
            txtdiscount.setText("");
            txtprofile.setText("");
            txtsaleCinema.setText("");
            txtsaleMovie.setText("");
            txtsaleSession.setText("");

            txtadmin.setDisable(true);
            txtanalyzed.setDisable(true);
            txtAddSession.setDisable(true);
            txtUpdateSession.setDisable(true);
            txtupdate.setDisable(true);
            txtdiscount.setDisable(true);
            txtprofile.setDisable(true);
            txtsaleCinema.setDisable(true);
            txtsaleMovie.setDisable(true);
            txtsaleSession.setDisable(true);
        }
    }

    public void Initialisation2(String name) throws IOException {
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        Connection con;
        ResultSet rs;
        Statement stat;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            stat = con.createStatement();
            rs = stat.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie JOIN cinema on session.Id_cinema=cinema.Id_cinema WHERE movie.Name='" + name + "' ");

            FXMLLoader fxmlLoaderBook = new FXMLLoader(MainApplication.class.getResource("Book.fxml"));
            AnchorPane bookPane = fxmlLoaderBook.load();
            BookController bc = fxmlLoaderBook.getController();

            Set<String> uniqueCinemas = new HashSet<>();

            ObservableList<String> items = FXCollections.observableArrayList();
            ObservableList<String> items2 = FXCollections.observableArrayList();

            while (rs.next()) {
                String cinema = rs.getString("cinema.name");
                if (!uniqueCinemas.contains(cinema)) {
                    uniqueCinemas.add(cinema);
                    String date = rs.getString("Date");
                    items.add(cinema);
                    items2.add(date);
                    bc.setBook(rs.getString("poster"), rs.getString("Name"), items, items2, account, rs.getString("Resume"));
                }
            }

            MovieContainer.add(bookPane, 0, 1);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void payment(int id_session, String id_movie, int cpt, int[] tab) {
        MovieContainer.getChildren().clear();
        MovieContainer.getColumnConstraints().clear();
        MovieContainer.getRowConstraints().clear();

        if (account == 0) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs2 = stat.executeQuery("SELECT * FROM session JOIN movie ON session.ID_movie = movie.ID_movie WHERE session.ID_session = " + id_session + " AND session.ID_movie=" + id_movie + "");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Payment.fxml"));
                AnchorPane paymentPane = fxmlLoader.load();

                PaymentController pc = fxmlLoader.getController();
                MovieContainer.add(paymentPane, 0, 1);

                while (rs2.next()) {
                    price_tick = rs2.getInt("Price");
                    discount = rs2.getInt("Discount");
                    id_room = rs2.getInt("ID_room");
                    pc.setPayment(null, "", rs2.getString("poster"), rs2.getString("Name"), cpt, price_tick, discount, id_session, tab, id_room);
                }
                con.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (account != 0) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs2 = stat.executeQuery("SELECT * FROM session JOIN movie ON session.ID_movie = movie.ID_movie WHERE session.ID_session = " + id_session + " AND session.ID_movie=" + id_movie + "");

                while (rs2.next()) {
                    price_tick = rs2.getInt("Price");
                    discount = rs2.getInt("Discount");
                    poster = rs2.getString("poster");
                    name = rs2.getString("Name");
                    id_room = rs2.getInt("ID_room");

                }
                con.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM users WHERE users.ID_user='"+account+"'");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Payment.fxml"));
                AnchorPane paymentPane = fxmlLoader.load();

                while (rs.next()) {
                    email = rs.getString("email");
                }
                PaymentController pc = fxmlLoader.getController();
                pc.setPayment(email,"",poster,name,cpt,price_tick,discount,id_session,tab,id_room);
                MovieContainer.add(paymentPane, 0, 1);
                con.close();

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
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

                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), account, rs.getString("Review"));


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

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController mc = fxmlLoader.getController();

                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), account, rs.getString("Review"));


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

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController mc = fxmlLoader.getController();

                mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), account, rs.getString("Review"));


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
            ResultSet rs = stat.executeQuery("SELECT * FROM session JOIN movie ON session.ID_movie=movie.ID_movie JOIN cinema ON cinema.Id_cinema=session.Id_cinema");

            while (rs.next())
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("SalesDiscount.fxml"));
                VBox SalesDiscount = fxmlLoader.load();
                SalesDiscountController sdc = fxmlLoader.getController();
                sdc.setDiscountInfo(rs.getString("movie.name"), rs.getString("cinema.name"), rs.getString("session.date"), rs.getString("session.ID_room"), rs.getString("movie.poster"), rs.getString("session.discount"), rs.getInt("session.ID_session"), account);
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
                    if(rs.getString("Name").contains(searchbar.getText()) || rs.getString("Genre").contains(searchbar.getText()) || rs.getString("Year").contains(searchbar.getText()) || rs.getString("Author").contains(searchbar.getText()))
                    {
                        find = true;
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("MovieHome.fxml"));
                        VBox movieBox = fxmlLoader.load();
                        MovieController mc = fxmlLoader.getController();
                        mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), account, rs.getString("Review"));

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
                        mc.SetMovie(rs.getString("poster"), rs.getString("Genre"), rs.getString("Name"), rs.getString("Year"), account, rs.getString("Review"));

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
    public void setVenue(){
        ObservableList<String> updatedItems = FXCollections.observableArrayList();
        if (!combo_movie.isDisable()) {
            combo_venue.setDisable(false);
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs3 = stat.executeQuery("SELECT * FROM cinema JOIN session ON cinema.Id_cinema=session.Id_cinema JOIN movie ON movie.ID_movie=session.ID_movie WHERE movie.Name = "+'"'+combo_movie.getSelectionModel().getSelectedItem()+'"');
                while (rs3.next()) {
                    String venue = rs3.getString("cinema.name");
                    if (!rs3.wasNull()) {
                        updatedItems.add(venue);
                    }
                }
                combo_venue.setItems(updatedItems);
                combo_movie.setDisable(true);
                con.close();
                stat.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void setDate(){
        ObservableList<String> updatedItems = FXCollections.observableArrayList();
        if (!combo_venue.isDisable()) {
            combo_date.setDisable(false);
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs3 = stat.executeQuery("SELECT * FROM cinema JOIN session ON cinema.Id_cinema=session.Id_cinema JOIN movie ON movie.ID_movie=session.ID_movie WHERE movie.Name = "+'"'+combo_movie.getSelectionModel().getSelectedItem()+'"' + " AND cinema.name ="+'"'+combo_venue.getSelectionModel().getSelectedItem()+'"');
                while (rs3.next()) {
                    String Date = rs3.getString("session.Date");
                    if (!rs3.wasNull()) {
                        updatedItems.add(Date);
                    }
                }
                combo_date.setItems(updatedItems);
                combo_venue.setDisable(true);
                con.close();
                stat.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void setSeat(){
        ObservableList<String> updatedItems = FXCollections.observableArrayList();
        if (!combo_date.isDisable()) {
            combo_seat.setDisable(false);
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs3 = stat.executeQuery("SELECT * FROM session WHERE session.Date = "+'"'+combo_date.getSelectionModel().getSelectedItem()+'"');
                while (rs3.next()) {
                    for(int i=0; i<30;i++){
                        int seat = rs3.getInt("session.seat_" + (i+1));
                        if (!rs3.wasNull() && seat==0) {
                            updatedItems.add(String.valueOf(i+1));
                        }
                    }
                }
                combo_seat.setItems(updatedItems);
                combo_date.setDisable(true);
                con.close();
                stat.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void setBook(){
        combo_seat.setDisable(true);
    }

    @FXML
    public void Reset()
    {
        combo_seat.getItems().clear();
        combo_movie.getItems().clear();
        combo_venue.getItems().clear();
        combo_date.getItems().clear();

        combo_venue.setDisable(true);
        combo_date.setDisable(true);
        combo_seat.setDisable(true);
        combo_movie.setDisable(false);

        ObservableList<String> movie = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM movie");

            while (rs.next()) {
                String movie_name = rs.getString("Name");
                movie.add(movie_name);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        combo_movie.setItems(movie);
    }

    @FXML
    public void quickBook()
    {
        int[] tab = new int[30];
        if(combo_seat.isDisable())
        {
            String selectedDate = combo_date.getSelectionModel().getSelectedItem();
            String selectedCinema = combo_venue.getSelectionModel().getSelectedItem();

            if (combo_date.isDisable() && combo_venue.isDisable()) {
                if (selectedDate != null && selectedCinema != null) {
                    try {
                        Connection con4 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                        Statement stat4 = con4.createStatement();
                        ResultSet rs4 = stat4.executeQuery("SELECT * FROM session WHERE Date = '" + selectedDate + "' AND Id_cinema = (SELECT Id_cinema FROM cinema WHERE Name = '" + selectedCinema + "')");

                        if (rs4.next()) {
                            int seatNumber = Integer.parseInt(combo_seat.getSelectionModel().getSelectedItem());

                            for (int i = 0; i < 30; i++) {
                                if(i==seatNumber-1 || rs4.getInt("seat_"+(i+1))==1)
                                    tab[i] = 1;
                            }

                        }
                        rs4.close();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }

                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                    Statement stat = con.createStatement();
                    ResultSet rs = stat.executeQuery("SELECT * FROM movie JOIN session ON movie.ID_movie=session.ID_movie WHERE session.Date='"+combo_date.getSelectionModel().getSelectedItem()+"'");

                    while (rs.next()) {
                        payment(rs.getInt("session.ID_session"), rs.getString("ID_movie"),1,tab);
                    }

                    rs.close();
                    stat.close();
                    con.close();

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}
