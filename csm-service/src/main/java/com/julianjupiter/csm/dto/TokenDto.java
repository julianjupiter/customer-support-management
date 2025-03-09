package com.julianjupiter.csm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Julian Jupiter
 */
public record TokenDto(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("token_type")
        String tokenType) {
}
