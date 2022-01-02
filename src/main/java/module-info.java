module com.example.will_hero {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.will_hero to javafx.fxml;
    exports com.example.will_hero to javafx.graphics;
}