package com.julianjupiter.csm;

import com.julianjupiter.csm.controller.LoginController;
import com.julianjupiter.csm.util.Constants;
import com.julianjupiter.csm.util.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class CsmApplication extends Application {
    private Stage csmStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.csmStage = stage;
        var loginView = View.of(LoginController.class, BorderPane.class, resourceBundle());
        Scene scene = new Scene(loginView.component());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Constants.CSS_STYLE)).toExternalForm());
        this.csmStage.setScene(scene);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        var loginController = loginView.controller();
        loginController.setCsmStage(this.csmStage);
        this.csmStage.show();
    }
    private ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle(Constants.MESSAGES, Constants.LOCALE_EN);
    }

    public static void run(String[] args) {
        launch(args);
    }
}