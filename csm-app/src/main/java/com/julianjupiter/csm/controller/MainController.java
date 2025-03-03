package com.julianjupiter.csm.controller;

import com.julianjupiter.csm.core.Controller;
import com.julianjupiter.csm.util.Constants;
import com.julianjupiter.csm.viewmodel.LoginViewModel;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Julian Jupiter
 */
public class MainController implements Initializable, Controller {
    private ResourceBundle resourceBundle;
    private Stage csmStage;
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void setCsmStage(Stage csmStage) {
        this.csmStage = csmStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setTitle(this.resourceBundle.getString(Constants.APPLICATION_NAME));
        this.mainStage.initStyle(StageStyle.DECORATED);
        this.mainStage.setMaximized(true);
        this.mainStage.setOnCloseRequest(windowEvent -> {
            var loginViewModel = new LoginViewModel();
            loginViewModel.logout();
            Platform.exit();
        });
    }
}
