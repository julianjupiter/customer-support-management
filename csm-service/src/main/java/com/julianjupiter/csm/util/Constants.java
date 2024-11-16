package com.julianjupiter.csm.util;

/**
 * @author Julian Jupiter
 */
public class Constants {
    public static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String REGEX_PASSWORD = "^[^\\s]+$";
    public static final String REGEX_USERNAME = "^[a-zA-Z0-9]+$";

    private Constants() {
    }

    public static class MessageCode {
        public static final String EMAIL_ALREADY_EXISTS = "email.already-exists";
        public static final String USERNAME_ALREADY_EXISTS = "username.already-exists";

        private MessageCode() {
        }
    }
}
