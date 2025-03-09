module com.julianjupiter.csm {
    requires javafx.fxml;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.prefs;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.jfxtras.styles.jmetro;
    requires com.nimbusds.jose.jwt;
    requires java.net.http;
    requires org.slf4j;

    opens com.julianjupiter.csm to javafx.fxml;
    exports com.julianjupiter.csm;
    exports com.julianjupiter.csm.controller;
    exports com.julianjupiter.csm.security;
    opens com.julianjupiter.csm.controller to javafx.fxml;
}