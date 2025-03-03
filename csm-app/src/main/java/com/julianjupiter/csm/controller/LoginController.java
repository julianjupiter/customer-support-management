package com.julianjupiter.csm.controller;

import com.julianjupiter.csm.core.Controller;
import com.julianjupiter.csm.util.Constants;
import com.julianjupiter.csm.security.LoginStatus;
import com.julianjupiter.csm.util.View;
import com.julianjupiter.csm.viewmodel.LoginViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Controller, Initializable {
    private static final System.Logger LOGGER = System.getLogger(LoginController.class.getName());
    private final LoginViewModel loginViewModel;
    private ResourceBundle resourceBundle;
    private Stage csmStage;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private AnchorPane headerAnchorPaneLeft;
    @FXML
    private Label applicationNameLabel;
    @FXML
    private ImageView logoImageView;
    @FXML
    private AnchorPane headerAnchorPaneRight;
    @FXML
    private FontIcon windowMinimizeFontIcon;
    @FXML
    private FontIcon windowCloseFontIcon;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button clearButton;

    public LoginController() {
        this.loginViewModel = new LoginViewModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.initApplicationName();
        this.initApplicationLogo();
        this.initWindowEvents();
        this.bindViewModel();
    }

    @Override
    public void bindViewModel() {
        this.usernameTextField.textProperty().bindBidirectional(this.loginViewModel.usernameProperty());
        this.passwordTextField.textProperty().bindBidirectional(this.loginViewModel.passwordProperty());
        this.loginButton.disableProperty().bind(this.loginViewModel.loginPossibleProperty().not());
        this.clearButton.disableProperty().bind(this.loginViewModel.clearPossibleProperty().not());
    }

    public void setCsmStage(Stage loginStage) {
        this.csmStage = loginStage;
        this.csmStage.initStyle(StageStyle.UNDECORATED);
    }

    private void initApplicationName() {
        this.applicationNameLabel.setText(this.resourceBundle.getString(Constants.APPLICATION_NAME));
    }

    private void initApplicationLogo() {
        var image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(Constants.APPLICATION_LOGO)));
        this.logoImageView.setImage(image);
    }

    private void initWindowEvents() {
        this.headerAnchorPaneLeft.setOnMousePressed(mouseEvent -> {
            this.yOffset = mouseEvent.getSceneY();
            this.xOffset = mouseEvent.getSceneX();
        });

        this.headerAnchorPaneLeft.setOnMouseDragged(mouseEvent -> {
            this.csmStage.setX(mouseEvent.getScreenX() - this.xOffset);
            this.csmStage.setY(mouseEvent.getScreenY() - this.yOffset);
        });

        this.headerAnchorPaneRight.setOnMousePressed(mouseEvent -> {
            this.xOffset = mouseEvent.getSceneX();
            this.yOffset = mouseEvent.getSceneY();
        });

        this.headerAnchorPaneRight.setOnMouseDragged(mouseEvent -> {
            this.csmStage.setX(mouseEvent.getScreenX() - this.xOffset);
            this.csmStage.setY(mouseEvent.getScreenY() - this.yOffset);
        });

        this.windowCloseFontIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close Application");
            alert.setHeaderText("You are about to close the application.");
            alert.setContentText("Do you want to continue?");

            alert.initModality(Modality.WINDOW_MODAL);
            alert.initOwner(this.csmStage);

            alert.showAndWait()
                    .filter(buttonType -> buttonType == ButtonType.OK)
                    .ifPresent(buttonType -> Platform.exit());
        });

        this.windowMinimizeFontIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> this.csmStage.setIconified(true));
    }

    @FXML
    private void login() {
        LOGGER.log(System.Logger.Level.INFO, () -> "LOGIN");

        this.loginViewModel.login();
        this.usernameTextField.setText("");
        this.passwordTextField.setText("");
        if (this.loginViewModel.getLoginStatus() == LoginStatus.FAILED) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Login");
            alert.setContentText("Unable to login. Please try again!");
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initOwner(this.csmStage);
            alert.showAndWait();
        } else {
            var mainView = View.of(MainController.class, BorderPane.class, resourceBundle);
            var mainController = mainView.controller();
            var mainStage = new Stage();
            var scene = new Scene(mainView.component());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Constants.CSS_STYLE)).toExternalForm());
            mainStage.setScene(scene);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            mainController.setCsmStage(this.csmStage);
            mainController.setMainStage(mainStage);
            this.csmStage.close();
            mainStage.show();
        }
    }

    @FXML
    private void clear() {
        this.loginViewModel.usernameProperty().setValue("");
        this.loginViewModel.passwordProperty().setValue("");
    }
}