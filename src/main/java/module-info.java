module com.example.movie_ticket_booking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.movie_ticket_booking to javafx.fxml;
    exports com.example.movie_ticket_booking;
}