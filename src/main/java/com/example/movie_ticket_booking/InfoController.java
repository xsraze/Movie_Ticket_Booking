package com.example.movie_ticket_booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InfoController {
    @FXML
    private Label txtEmail;

    @FXML
    private Label txtLastName;

    @FXML
    private Label txtName;

    @FXML
    private Label txtPassword;

    @FXML
    private Label txtPhone;

    @FXML
    private Label txtType;

    @FXML
    private Label txtUsername;
    @FXML
    private Label txtWelcome;

    public void SetInfo(int account){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_london?useSSL=FALSE", "root", "");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM `users`");

            while (rs.next())
            {
                if(account == Integer.parseInt(rs.getString("ID_user")))
                {
                    txtWelcome.setText("Welcome "+rs.getString("Name")+"!");
                    txtType.setText(rs.getString("Type"));
                    txtPhone.setText(rs.getString("phone"));
                    txtUsername.setText(rs.getString("username"));
                    txtName.setText(rs.getString("Name"));
                    txtLastName.setText(rs.getString("LastName"));
                    txtEmail.setText(rs.getString("email"));
                    txtPassword.setText(rs.getString("password"));
                }

            }
            con.close();
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }
}
