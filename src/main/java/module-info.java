module com.example.zombiesprueba {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.logging;
    requires javafx.media;

    opens zombieSurvival to javafx.fxml;
    exports zombieSurvival;
    exports zombieSurvival.configuracionesAdicionales;
    opens zombieSurvival.configuracionesAdicionales to javafx.fxml;
}