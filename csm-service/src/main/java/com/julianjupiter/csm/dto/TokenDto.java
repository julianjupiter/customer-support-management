package com.julianjupiter.csm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Julian Jupiter
 */
public record TokenDto(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_in")
        Date expiresIn,
        @JsonProperty("token_type")
        String tokenType) {
}
