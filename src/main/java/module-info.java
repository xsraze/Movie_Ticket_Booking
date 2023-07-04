module com.example.movie_ticket_booking {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.movie_ticket_booking to javafx.fxml;
    exports com.example.movie_ticket_booking.Controller;
    opens com.example.movie_ticket_booking.Controller to javafx.fxml;
    exports com.example.movie_ticket_booking.Model;
    opens com.example.movie_ticket_booking.Model to javafx.fxml;
}