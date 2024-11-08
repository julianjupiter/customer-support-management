package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.TokenDto;
import org.springframework.security.core.Authentication;

/**
 * @author Julian Jupiter
 */
public interface JwtService {
    TokenDto generateToken(Authentication authentication);

    void validateToken(String accessToken);

    String extractUsername(String accessToken);
}
