package com.julianjupiter.csm.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public class SecurityUtil {
    private SecurityUtil() {

    }

    public static void setToken(TokenDto tokenDto) {
        SecurityStorage.store(tokenDto, authenticatedUser(tokenDto));
    }

    public static Optional<String> accessToken() {
        try {
            return Optional.of(SecurityStorage.accessToken());
        } catch (SecurityException e) {
            return Optional.empty();
        }
    }

    public static Optional<AuthenticatedUser> authenticatedUser() {
        try {
            return Optional.of(SecurityStorage.authenticatedUser());
        } catch (SecurityException e) {
            return Optional.empty();
        }
    }

    public static boolean isActive() {
        return SecurityStorage.tokenExpiresIn().before(new Date());
    }

    public static void remove() {
        SecurityStorage.remove();
    }

    public static Optional<String> tokenType() {
        try {
            return Optional.of(SecurityStorage.tokenType());
        } catch (SecurityException e) {
            return Optional.empty();
        }
    }

    private static AuthenticatedUser authenticatedUser(TokenDto tokenDto) {
        var objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(tokenDto.accessToken(), AuthenticatedUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
