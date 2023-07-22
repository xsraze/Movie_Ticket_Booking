package com.example.movie_ticket_booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserController {
    public ToggleGroup type;
    @FXML
    private RadioButton RadCustomer;

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

    private int ID;
    private int ID_admin;

    @FXML
    void DeleteProfile(ActionEvent event) throws IOException {
        //Button to delete a User by its id
        String request;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");
            Statement stat = con.createStatement();

            request = "DELETE FROM `users` WHERE ID_user = '" + ID + "'";
            stat.executeUpdate(request);
        }
        catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
        //sends to the home page
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
        Parent root = fxmlLoader.load();
        Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        HomeController hc = fxmlLoader.getController();
        hc.Initialisation(ID_admin);
        hc.UpdateProfiles();
        Scene scene = new Scene(root);
        lstage.setScene(scene);
        lstage.show();

    }

    @FXML
    void UpdateProfile(ActionEvent event) {
        //modifying the information of a profile
        boolean error= txtEmail.getText().length() == 0;

        if(txtName.getText().length()==0)
        {
            //check the name
            error=true;
        }
        if(txtLastName.getText().length()==0)
        {
            //check the last name
            error=true;
        }
        if(txtPassword.getText().length()==0)
        {
            //check the password
            error=true;
        }
        if(txtUsername.getText().length()==0)
        {
            //check the username
            error=true;
        }
        if(txtPhone.getText().length()==0)
        {
            //check the phone number
            error=true;
        }
        if(!radAdmin.isSelected() && !RadCustomer.isSelected())
        {
            //check if it's an admin or a customer
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
                    if(txtEmail.getText().equals(rs.getString("email")) && ID!=rs.getInt("ID_user"))
                    {
                        //check the email
                        verified=true;
                    }
                    if(txtUsername.getText().equals(rs.getString("username")) && ID != rs.getInt("ID_user"))
                    {
                        //check the username
                         verified=true;
                    }
                    if(txtPhone.getText().equals(rs.getString("phone"))  && ID != rs.getInt("ID_user"))
                    {
                        //check the phonenumber
                        verified=true;
                    }
                }
                //if everything is good, updates the database
                if(!verified)
                {
                    if(radAdmin.isSelected())
                        request ="UPDATE `users` SET `Name`='"+txtName.getText()+"',`LastName`='"+txtLastName.getText()+"',`email`='"+txtEmail.getText()+"',`phone`='"+txtPhone.getText()+"',`username`='"+txtUsername.getText()+"',`password`='"+txtPassword.getText()+"',`Type`='admin' WHERE ID_user = '"+ID+"'";


                    else
                        request ="UPDATE `users` SET `Name`='"+txtName.getText()+"',`LastName`='"+txtLastName.getText()+"',`email`='"+txtEmail.getText()+"',`phone`='"+txtPhone.getText()+"',`username`='"+txtUsername.getText()+"',`password`='"+txtPassword.getText()+"',`Type`='customer' WHERE ID_user = '"+ID+"'";

                    stat.executeUpdate(request);
                    //sends to the homepage
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                    HomeController hc = fxmlLoader.getController();
                    hc.Initialisation(ID_admin);
                    hc.UpdateProfiles();
                    Scene scene = new Scene(root);
                    lstage.setScene(scene);
                    lstage.show();
                }

            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

    public void setUser(String name, String LastName, String email, String phone, String username, String password, String type, int id, int id_admin)
    {
        //sets the information for the user
        ID_admin=id_admin;
        ID=id;
        txtName.setText(name);
        txtLastName.setText(LastName);
        txtPhone.setText(phone);
        txtEmail.setText(email);
        txtUsername.setText(username);
        txtPassword.setText(password);
        if(type.equals("customer"))
            RadCustomer.setSelected(true);
        else
            radAdmin.setSelected(true);
    }
}
