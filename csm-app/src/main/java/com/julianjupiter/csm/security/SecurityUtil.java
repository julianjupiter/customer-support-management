package com.julianjupiter.csm.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.time.Instant;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public class SecurityUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private SecurityUtil() {

    }

    public static void setToken(TokenDto tokenDto) {
        SecurityStorage.store(tokenDto, extractPayload(tokenDto.accessToken()));
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

    public static Instant tokenIssuedAt() {
        return SecurityStorage.tokenIssuedAt();
    }

    public static Instant tokenExpiresAt() {
        return SecurityStorage.tokenExpiresAt();
    }

    public static boolean isTokenActive() {
        return Instant.now().isBefore(SecurityStorage.tokenExpiresAt());
    }

    public static void remove(boolean debug) {
        SecurityStorage.remove();

        if (debug) {
            show();
        }
    }

    public static void show() {
        SecurityStorage.show();
    }

    public static Optional<String> tokenType() {
        try {
            return Optional.of(SecurityStorage.tokenType());
        } catch (SecurityException e) {
            return Optional.empty();
        }
    }

    private static TokenPayload extractPayload(String accessToken) {
        try {
            var decodedJWT = SignedJWT.parse(accessToken);
            var payload = decodedJWT.getPayload().toString();
            return OBJECT_MAPPER.readValue(payload, TokenPayload.class);
        } catch (ParseException e) {
            throw new SecurityException("Invalid access token");
        } catch (JsonProcessingException e) {
            throw new SecurityException("Error JSON processing of access token payload");
        }
    }
}
