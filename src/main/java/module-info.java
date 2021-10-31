module com.example.project3softwaremeth {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project3softwaremeth to javafx.fxml;
    exports com.example.project3softwaremeth;
}