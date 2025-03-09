package com.julianjupiter.csm.security;

import com.julianjupiter.csm.CsmApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

/**
 * @author Julian Jupiter
 */
class SecurityStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityStorage.class);
    private static final String CSM_ACCESS_TOKEN = "CSM_ACCESS_TOKEN";
    private static final String CSM_TOKEN_ISSUED_AT = "CSM_TOKEN_ISSUED_AT";
    private static final String CSM_TOKEN_EXPIRES_AT = "CSM_TOKEN_EXPIRES_AT";
    private static final String CSM_TOKEN_TYPE = "CSM_TOKEN_TYPE";
    private static final String CSM_USER_ID = "CSM_USER_ID";
    private static final String CSM_USERNAME = "CSM_USERNAME";
    private static final String CSM_USER_EMAIL = "CSM_USER_EMAIL";
    private static final String CSM_USER_NAME = "CSM_USER_NAME";
    private static final String CSM_USER_FIRST_NAME = "CSM_USER_FIRST_NAME";
    private static final String CSM_USER_LAST_NAME = "CSM_USER_LAST_NAME";
    private static final String CSM_USER_ROLES = "CSM_USER_ROLES";

    private static final Preferences preferences = Preferences.userRoot().node(CsmApplication.class.getName());

    private SecurityStorage() {

    }

    public static void store(TokenDto tokenDto, TokenPayload tokenPayload) {
        preferences.put(CSM_ACCESS_TOKEN, tokenDto.accessToken());
        preferences.put(CSM_TOKEN_TYPE, tokenDto.tokenType());
        preferences.put(CSM_TOKEN_ISSUED_AT, String.valueOf(tokenPayload.iat()));
        preferences.put(CSM_TOKEN_EXPIRES_AT, String.valueOf(tokenPayload.exp()));
        preferences.put(CSM_USER_ID, tokenPayload.userId());
        preferences.put(CSM_USERNAME, tokenPayload.username());
        preferences.put(CSM_USER_EMAIL, tokenPayload.email());
        preferences.put(CSM_USER_NAME, tokenPayload.name());
        preferences.put(CSM_USER_FIRST_NAME, tokenPayload.firstName());
        preferences.put(CSM_USER_LAST_NAME, tokenPayload.firstName());
        preferences.put(CSM_USER_ROLES, combineRoles(tokenPayload));
    }

    public static void remove() {
        preferences.remove(CSM_ACCESS_TOKEN);
        preferences.remove(CSM_TOKEN_TYPE);
        preferences.remove(CSM_TOKEN_ISSUED_AT);
        preferences.remove(CSM_TOKEN_EXPIRES_AT);
        preferences.remove(CSM_USER_ID);
        preferences.remove(CSM_USERNAME);
        preferences.remove(CSM_USER_EMAIL);
        preferences.remove(CSM_USER_NAME);
        preferences.remove(CSM_USER_FIRST_NAME);
        preferences.remove(CSM_USER_LAST_NAME);
        preferences.remove(CSM_USER_ROLES);
    }

    public static String accessToken() {
        var accessToken = preferences.get(CSM_ACCESS_TOKEN, null);
        if (isNotNullAndNotBlank(accessToken)) {
            return accessToken;
        } else {
            throw new SecurityException("Access token not existing");
        }
    }

    public static Instant tokenIssuedAt() {
        return Instant.ofEpochSecond(preferences.getLong(CSM_TOKEN_ISSUED_AT, 0L));
    }

    public static Instant tokenExpiresAt() {
        return Instant.ofEpochSecond(preferences.getLong(CSM_TOKEN_EXPIRES_AT, 0L));
    }

    public static String tokenType() {
        var tokenType = preferences.get(CSM_TOKEN_TYPE, null);
        if (isNotNullAndNotBlank(tokenType)) {
            return tokenType;
        } else {
            throw new SecurityException("Token type not existing");
        }
    }

    public static AuthenticatedUser authenticatedUser() {
        String userId = preferences.get(CSM_USER_ID, null);
        String username = preferences.get(CSM_USERNAME, null);
        String email = preferences.get(CSM_USER_EMAIL, null);
        String name = preferences.get(CSM_USER_FIRST_NAME, null);
        String firstName = preferences.get(CSM_USER_NAME, null);
        String lastName = preferences.get(CSM_USER_LAST_NAME, null);
        String roles = preferences.get(CSM_USER_ROLES, null);
        if (isNotNullAndNotBlank(userId)) {
            return new AuthenticatedUser(
                    userId,
                    username,
                    email,
                    name,
                    firstName,
                    lastName,
                    splitRoles(roles)
            );
        } else {
            throw new SecurityException("Unauthenticated user");
        }
    }

    public static void show() {
        String accessToken = preferences.get(CSM_ACCESS_TOKEN, null);
        String tokenType = preferences.get(CSM_TOKEN_TYPE, null);
        long tokenIssuedAt = preferences.getLong(CSM_TOKEN_ISSUED_AT, 0L);
        long tokenExpiresAt = preferences.getLong(CSM_TOKEN_EXPIRES_AT, 0L);
        String userId = preferences.get(CSM_USER_ID, null);
        String username = preferences.get(CSM_USERNAME, null);
        String email = preferences.get(CSM_USER_EMAIL, null);
        String name = preferences.get(CSM_USER_NAME, null);
        String firstName = preferences.get(CSM_USER_FIRST_NAME, null);
        String lastName = preferences.get(CSM_USER_LAST_NAME, null);
        String roles = preferences.get(CSM_USER_ROLES, null);
        var data = """
                
                Access Token : %s
                Token Type   : %s
                Issued At    : %s
                Expires At   : %s
                User ID      : %s
                Username     : %s
                Email        : %s
                Name         : %s
                First Name   : %s
                Last Name    : %s
                Roles        : %s
                """.formatted(
                accessToken,
                tokenType,
                tokenIssuedAt != 0L ? Instant.ofEpochSecond(tokenIssuedAt) : null,
                tokenExpiresAt != 0L ? Instant.ofEpochSecond(tokenExpiresAt) : null,
                userId,
                username,
                email,
                name,
                firstName,
                lastName,
                roles
        );
        LOGGER.info("{}", data);
    }

    private static String combineRoles(TokenPayload tokenPayload) {
        return String.join(",", tokenPayload.roles().stream()
                .map(Role::name)
                .collect(Collectors.toSet())
        );
    }

    private static List<Role> splitRoles(String roles) {
        return Arrays.stream(roles.split(","))
                .filter(s -> !s.isBlank())
                .map(Role::valueOf)
                .toList();
    }

    private static boolean isNotNullAndNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
