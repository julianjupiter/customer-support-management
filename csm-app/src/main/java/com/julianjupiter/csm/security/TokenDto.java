package com.julianjupiter.csm.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Julian Jupiter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TokenDto(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType) {
}
