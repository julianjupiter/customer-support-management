package com.julianjupiter.csm.security;

import java.util.List;

/**
 * @author Julian Jupiter
 */
public record AuthenticatedUser(
        String userId,
        String username,
        String email,
        String firstName,
        String lastName,
        List<Role>roles) {
}
