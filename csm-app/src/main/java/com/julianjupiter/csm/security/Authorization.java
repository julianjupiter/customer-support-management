package com.julianjupiter.csm.security;

/**
 * @author Julian Jupiter
 */
public class Authorization {
    private final String tokenType;
    private final String accessToken;

    private Authorization(String tokenType, String accessToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }

    public static Authorization create() {
        var accessToken = SecurityUtil.accessToken()
                .orElseThrow(() -> new SecurityException("No access token available"));
        var tokenType = SecurityUtil.tokenType().orElseGet(() -> "Bearer");

        return new Authorization(tokenType, accessToken);
    }

    @Override
    public String toString() {
        return this.tokenType + " " + this.accessToken;
    }
}
