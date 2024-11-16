package com.julianjupiter.csm.dto;

import com.julianjupiter.csm.util.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * @author Julian Jupiter
 */
public record CreateUserRequestDto(
        @NotNull
        @Pattern(regexp = Constants.REGEX_USERNAME)
        String username,
        @NotNull
        @Email(regexp = Constants.REGEX_EMAIL)
        String email,
        @NotNull
        @Pattern(regexp = Constants.REGEX_PASSWORD)
        String password,
        @NotNull
        @Size(min = 2)
        String firstName,
        @NotNull
        @Size(min = 2)
        String lastName) {
}
