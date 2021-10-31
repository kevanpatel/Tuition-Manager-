module com.example.project3softwaremeth {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.project3softwaremeth to javafx.fxml;
    exports com.example.project3softwaremeth;
}