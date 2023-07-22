package com.example.movie_ticket_booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistrationController {
    public ToggleGroup type;
    @FXML
    private RadioButton RadCust;

    @FXML
    private RadioButton radAdmin;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label ErrEmail;

    @FXML
    private Label ErrLastName;

    @FXML
    private Label ErrName;

    @FXML
    private Label ErrPassword;

    @FXML
    private Label ErrPhone;

    @FXML
    private Label ErrType;

    @FXML
    private Label ErrUsername;

    @FXML
    void Register(ActionEvent event) {
        boolean error=false;

        ErrEmail.setText("");
        ErrName.setText("");
        ErrType.setText("");
        ErrPhone.setText("");
        ErrUsername.setText("");
        ErrPassword.setText("");
        ErrLastName.setText("");

        if(txtEmail.getText().length()==0)
        {
            //error message for the email
            ErrEmail.setText("Put an Email");
            error=true;
        }
        if(txtName.getText().length()==0)
        {
            //error message for the name
            ErrName.setText("Put a Name");
            error=true;
        }
        if(txtLastName.getText().length()==0)
        {
            //error message for the last name
            ErrLastName.setText("Put a Last Name");
            error=true;
        }
        if(txtPassword.getText().length()==0)
        {
            //error message for the password
            ErrPassword.setText("Put a Password");
            error=true;
        }
        if(txtUsername.getText().length()==0)
        {
            //error message for the username
            ErrUsername.setText("Put a Username");
            error=true;
        }
        if(txtPhone.getText().length()==0)
        {
            //error message for the phone number
            ErrPhone.setText("Put a Phone Number");
            error=true;
        }
        if(!radAdmin.isSelected() && !RadCust.isSelected())
        {
            //error message for the type of account
            ErrType.setText("Put a Type account");
            error=true;
        }

        if(!error)
        {
            String request;

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
                Statement stat = con.createStatement();
                boolean verified=false;

                ResultSet rs = stat.executeQuery("SELECT * FROM `users`");

                while (rs.next())
                {
                    if(txtEmail.getText().equals(rs.getString("email")))
                    {
                        //if the email already exists
                        ErrEmail.setText("This Email already exists");
                        verified=true;
                    }
                    if(txtUsername.getText().equals(rs.getString("username")))
                    {
                        //if the username already exists
                        ErrUsername.setText("This Username already exists");
                        verified=true;
                    }
                    if(txtPhone.getText().equals(rs.getString("phone")))
                    {
                        //if the phone number already exists
                        ErrPhone.setText("This Phone number already exists");
                        verified=true;
                    }
                }
                //if everything is correct, we can insert in the database
                if(!verified)
                {
                    if(radAdmin.isSelected())
                        request = "INSERT INTO `users` (`ID_user`, `Name`, `LastName`, `email`, `phone`, `username`, `password`, `Type`) VALUES (NULL, '"+txtName.getText()+"', '"+txtLastName.getText()+"', '"+txtEmail.getText()+"', '"+txtPhone.getText()+"', '"+txtUsername.getText()+"', '"+txtPassword.getText()+"', 'admin');";

                    else
                        request = "INSERT INTO `users` (`ID_user`, `Name`, `LastName`, `email`, `phone`, `username`, `password`, `Type`) VALUES (NULL, '"+txtName.getText()+"', '"+txtLastName.getText()+"', '"+txtEmail.getText()+"', '"+txtPhone.getText()+"', '"+txtUsername.getText()+"', '"+txtPassword.getText()+"', 'customer');";

                    stat.executeUpdate(request);


                    ResultSet rs2 = stat.executeQuery("SELECT * FROM `users`");

                    while (rs2.next())
                    {
                        //sends to the home page
                        if(rs2.getString("username").equals(txtUsername.getText()))
                        {

                            int account = Integer.parseInt(rs2.getString("ID_user"));

                            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                            HomeController hc = fxmlLoader.getController();
                            hc.Initialisation(account);
                            Scene scene = new Scene(root);
                            lstage.setScene(scene);
                            lstage.show();
                        }
                    }
                }

            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }
    }
}
