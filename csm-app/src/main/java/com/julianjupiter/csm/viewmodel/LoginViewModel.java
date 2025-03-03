package com.julianjupiter.csm.viewmodel;

import com.julianjupiter.csm.repository.LoginRepository;
import com.julianjupiter.csm.security.SecurityUtil;
import com.julianjupiter.csm.security.LoginStatus;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.lang.System.Logger;

/**
 * @author Julian Jupiter
 */
public class LoginViewModel {
    private static final Logger LOGGER = System.getLogger(LoginViewModel.class.getName());
    private final LoginRepository loginRepository;
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final ReadOnlyBooleanWrapper loginPossible = new ReadOnlyBooleanWrapper();
    private final ReadOnlyBooleanWrapper clearPossible = new ReadOnlyBooleanWrapper();
    private LoginStatus loginStatus;

    public LoginViewModel() {
        this.loginRepository = new LoginRepository();
        this.loginPossible.bind(this.username.isNotEmpty().and(this.password.isNotEmpty()));
        this.clearPossible.bind(this.username.isNotEmpty().or(this.password.isNotEmpty()));
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public ReadOnlyBooleanWrapper loginPossibleProperty() {
        return loginPossible;
    }

    public ReadOnlyBooleanWrapper clearPossibleProperty() {
        return clearPossible;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void login() {
        LOGGER.log(Logger.Level.INFO, () -> "LOGIN");

        this.loginRepository.login(this.getUsername(), this.getPassword());

        SecurityUtil.authenticatedUser()
                .ifPresentOrElse(authenticatedUser -> this.loginStatus = LoginStatus.SUCCESS, () -> this.loginStatus = LoginStatus.FAILED);
    }

    public void logout() {
        LOGGER.log(System.Logger.Level.INFO, () -> "LOGOUT");

        SecurityUtil.remove();
    }
}
