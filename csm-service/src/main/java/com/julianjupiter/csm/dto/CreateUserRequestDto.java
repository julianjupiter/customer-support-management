package com.julianjupiter.csm.dto;

/**
 * @author Julian Jupiter
 */
public record CreateUserRequestDto(
        String username,
        String email,
        String password,
        String firstName,
        String lastName) {
}
