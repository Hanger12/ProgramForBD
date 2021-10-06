module com.example.laba4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.laba4 to javafx.fxml;
    exports com.example.laba4;
}