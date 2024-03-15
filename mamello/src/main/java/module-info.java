module com.example.mamello {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mamello to javafx.fxml;
    exports com.example.mamello;
}