package com.julianjupiter.csm.security;

import com.julianjupiter.csm.CsmApplication;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

/**
 * @author Julian Jupiter
 */
class SecurityStorage {
    private static final String CSM_ACCESS_TOKEN = "CSM_ACCESS_TOKEN";
    private static final String CSM_TOKEN_EXPIRES_IN = "CSM_TOKEN_EXPIRES_IN";
    private static final String CSM_TOKEN_TYPE = "CSM_TOKEN_TYPE";
    private static final String CSM_USER_ID = "CSM_USER_ID";
    private static final String CSM_USERNAME = "CSM_USERNAME";
    private static final String CSM_USER_EMAIL = "CSM_USER_EMAIL";
    private static final String CSM_USER_FIRST_NAME = "CSM_USER_FIRST_NAME";
    private static final String CSM_USER_LAST_NAME = "CSM_USER_LAST_NAME";
    private static final String CSM_USER_ROLES = "CSM_USER_ROLES";

    private static final Preferences preferences = Preferences.userRoot().node(CsmApplication.class.getName());

    private SecurityStorage() {

    }

    public static void store(TokenDto tokenDto, AuthenticatedUser authenticatedUser) {
        preferences.put(CSM_ACCESS_TOKEN, tokenDto.accessToken());
        preferences.putLong(CSM_TOKEN_EXPIRES_IN, tokenDto.expiresIn().getTime());
        preferences.put(CSM_TOKEN_TYPE, tokenDto.tokenType());
        preferences.put(CSM_USER_ID, authenticatedUser.userId());
        preferences.put(CSM_USERNAME, authenticatedUser.username());
        preferences.put(CSM_USER_EMAIL, authenticatedUser.email());
        preferences.put(CSM_USER_FIRST_NAME, authenticatedUser.firstName());
        preferences.put(CSM_USER_LAST_NAME, authenticatedUser.firstName());
        preferences.put(CSM_USER_ROLES, combineRoles(authenticatedUser));
    }

    public static void remove() {
        preferences.remove(CSM_ACCESS_TOKEN);
        preferences.remove(CSM_TOKEN_EXPIRES_IN);
        preferences.remove(CSM_TOKEN_TYPE);
        preferences.remove(CSM_USER_ID);
        preferences.remove(CSM_USERNAME);
        preferences.remove(CSM_USER_EMAIL);
        preferences.remove(CSM_USER_FIRST_NAME);
        preferences.remove(CSM_USER_LAST_NAME);
        preferences.remove(CSM_USER_ROLES);
    }

    public static String accessToken() {
        var accessToken = preferences.get(CSM_ACCESS_TOKEN, null);
        if (isNotNullAndNotBlack(accessToken)) {
            return accessToken;
        } else {
            throw new SecurityException("Access token not existing");
        }
    }

    public static Date tokenExpiresIn() {
        return new Date(preferences.getLong(CSM_TOKEN_EXPIRES_IN, 0L));
    }

    public static String tokenType() {
        var tokenType = preferences.get(CSM_TOKEN_TYPE, null);
        if (isNotNullAndNotBlack(tokenType)) {
            return tokenType;
        } else {
            throw new SecurityException("Token type not existing");
        }
    }

    public static AuthenticatedUser authenticatedUser() {
        String userId = preferences.get(CSM_USER_ID, null);
        String username = preferences.get(CSM_USERNAME, null);
        String email = preferences.get(CSM_USER_EMAIL, null);
        String firstName = preferences.get(CSM_USER_FIRST_NAME, null);
        String lastName = preferences.get(CSM_USER_LAST_NAME, null);
        String roles = preferences.get(CSM_USER_ROLES, null);
        if (isNotNullAndNotBlack(userId) &&
                isNotNullAndNotBlack(username) &&
                isNotNullAndNotBlack(email) &&
                isNotNullAndNotBlack(firstName) &&
                isNotNullAndNotBlack(lastName) &&
                isNotNullAndNotBlack(roles)) {
            return new AuthenticatedUser(
                    userId,
                    username,
                    email,
                    firstName,
                    lastName,
                    splitRoles(roles)
            );
        } else {
            throw new SecurityException("Unauthenticated user");
        }
    }

    private static String combineRoles(AuthenticatedUser authenticatedUser) {
        return String.join(",", authenticatedUser.roles().stream()
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

    private static boolean isNotNullAndNotBlack(String value) {
        return value != null && !value.isBlank();
    }
}
