module com.example.converter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.google.gson;

    opens com.example.converter to javafx.fxml, com.google.gson;
    exports com.example.converter;
}