package com.julianjupiter.csm.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Julian Jupiter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TokenPayload(
        String jti,
        String iss,
        String sub,
        long iat,
        long exp,
        @JsonProperty("user_id")
        String userId,
        String username,
        String name,
        String email,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        List<Role> roles) {
}
