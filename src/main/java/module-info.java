module com.example.movie_ticket_booking {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.movie_ticket_booking to javafx.fxml;
    exports com.example.movie_ticket_booking;
}