module com.julianjupiter.csm {
    requires javafx.fxml;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.prefs;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.jfxtras.styles.jmetro;

    opens com.julianjupiter.csm to javafx.fxml;
    exports com.julianjupiter.csm;
    exports com.julianjupiter.csm.controller;
    opens com.julianjupiter.csm.controller to javafx.fxml;
}