package com.example.movie_ticket_booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.*;

public class PaymentController {
    @FXML
    private TextField cb;

    @FXML
    private Label discount;

    @FXML
    private TextField email;

    @FXML
    private Label final_p;

    @FXML
    private Label nb_tickets;

    @FXML
    private Label subtotal1;

    @FXML
    private Label subtotal2;

    @FXML
    private ImageView poster;

    @FXML
    private Label titre_movie;

    private int id_sess;
    private int[] seat;
    private int id_room;
    private boolean verif = false;

    public void setPayment(String email2, String cb2, String post, String title, int nb_tick, int price, int disc, int id_session, int[] tab, int id_rooom) {

        int fp=nb_tick * price;
        email.setText(email2);
        cb.setText(cb2);
        poster.setImage(new Image(post));
        titre_movie.setText(title);
        nb_tickets.setText("" + nb_tick);
        subtotal1.setText("£ " + nb_tick * price);
        subtotal2.setText("£ " + nb_tick * price);
        discount.setText("0" + " %");
        final_p.setText("£ " + fp);


        if (email.getText() != null) {
            fp = nb_tick * price - nb_tick * price * disc / 100;
            email.setDisable(true);
            discount.setText(disc + " %");
            final_p.setText("£ " + fp);
            verif=true;
        }
        id_sess = id_session;
        seat = tab;
        id_room = id_rooom;
    }

    @FXML
    void pay(ActionEvent event) {
        String emailAddress = email.getText();
        String cardNumber = cb.getText();
        int ticketCount = Integer.parseInt(nb_tickets.getText());
        int discountPercentage = Integer.parseInt(discount.getText().substring(0, discount.getText().length() - 2));
        int finalPrice = Integer.parseInt(final_p.getText().substring(2));
        boolean verif_cb=false;
        boolean verif_email=false;

        if (emailAddress.trim().isEmpty() || !isValidEmail(emailAddress)) {
            showErrorMessage("Please enter a correct adress email.");
            verif_cb=true;
        }

        if (cardNumber.trim().isEmpty() || cardNumber.length() < 16 || cardNumber.length() > 19) {
            showErrorMessage("Please enter a correct card number (between 16 and 19 numbers).");
            verif_email=true;
        }

        if (!verif_cb && !verif_email) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();

                String request = "UPDATE `session` SET `seat_1`='" + seat[0] + "',`seat_2`='" + seat[1] + "',`seat_3`='" + seat[2] + "',`seat_4`='" + seat[3] + "',`seat_5`='" + seat[4] + "',`seat_6`='" + seat[5] + "',`seat_7`='" + seat[6] + "',`seat_8`='" + seat[7] + "',`seat_9`='" + seat[8] + "',`seat_10`='" + seat[9] + "',`seat_11`='" + seat[10] + "',`seat_12`='" + seat[11] + "',`seat_13`='" + seat[12] + "',`seat_14`='" + seat[13] + "',`seat_15`='" + seat[14] + "',`seat_16`='" + seat[15] + "',`seat_17`='" + seat[16] + "',`seat_18`='" + seat[17] + "',`seat_19`='" + seat[18] + "',`seat_20`='" + seat[19] + "',`seat_21`='" + seat[20] + "',`seat_22`='" + seat[21] + "',`seat_23`='" + seat[22] + "',`seat_24`='" + seat[23] + "',`seat_25`='" + seat[5] + "',`seat_26`='" + seat[25] + "',`seat_27`='" + seat[26] + "',`seat_28`='" + seat[27] + "',`seat_29`='" + seat[28] + "',`seat_30`='" + seat[29] + "' WHERE ID_session ='" + id_sess + "'";
                stat.executeUpdate(request);

                stat.close();
                con.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();

                String query = "INSERT INTO reservation (FinalDiscount, FinalPrice, Nb_tickets, ID_session, Email_adress, ID_room, Card_number) VALUES (" + discountPercentage + ", " + finalPrice + ", " + ticketCount + ", " + id_sess + ", '" + emailAddress + "', '" + id_room + "', '" + cardNumber + "')";

                stat.executeUpdate(query);

                stat.close();
                con.close();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Payment succeeded");
                alert.setHeaderText(null);
                alert.setContentText("Your payment has been allowed. Thank you for your reservation !");

                alert.showAndWait();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM users WHERE users.email='"+email.getText()+"'");
                boolean find=false;
                int account=0;

                while(rs.next())
                {
                    find=true;
                    account=rs.getInt("ID_user");
                }

                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
                Parent root = fxmlLoader.load();
                Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                HomeController hc = fxmlLoader.getController();

                if(find)
                {
                    hc.Initialisation(account);
                    Scene scene = new Scene(root);
                    lstage.setScene(scene);
                }

                else
                {
                    hc.Initialisation(0);
                    Scene scene = new Scene(root);
                    lstage.setScene(scene);
                }
                lstage.show();


            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isValidEmail(String email) {
        if(!verif) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                PreparedStatement stat = con.prepareStatement("SELECT * FROM reservation WHERE Email_adress = ?");
                stat.setString(1, email);
                ResultSet rs = stat.executeQuery();
                if (rs.next()) {
                    return false;
                }
                stat.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }




    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
