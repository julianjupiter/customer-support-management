package com.julianjupiter.csm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * @author Julian Jupiter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponseDto(
        int statusCode,
        String reasonPhrase,
        Object data,
        Instant timestamp) {
    public static ApiResponseDto of(HttpStatus status) {
        return new ApiResponseDto(status.value(), status.getReasonPhrase(), null, Instant.now());
    }

    public static ApiResponseDto of(HttpStatus status, Object data) {
        return new ApiResponseDto(status.value(), status.getReasonPhrase(), data, Instant.now());
    }
}
