package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.TokenDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Julian Jupiter
 */
public interface JwtService {
    TokenDto generateToken(Authentication authentication);

    boolean validateToken(String accessToken);

    String extractUsername(String accessToken);
}
