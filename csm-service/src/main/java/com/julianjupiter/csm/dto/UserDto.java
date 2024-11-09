package com.julianjupiter.csm.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * @author Julian Jupiter
 */
public record UserDto(
        UUID id,
        String username,
        String email,
        String firstName,
        String middleName,
        String lastName,
        String extensionName,
        String mobileNumber,
        Boolean accountNonExpired,
        Boolean accountNonLocked,
        Boolean credentialsNonExpired,
        Boolean enabled,
        Boolean emailVerified,
        Boolean mobileNumberVerified,
        Set<RoleDto> roles,
        String createdBy,
        Instant createdAt,
        String updatedBy,
        Instant updatedAt) {
}