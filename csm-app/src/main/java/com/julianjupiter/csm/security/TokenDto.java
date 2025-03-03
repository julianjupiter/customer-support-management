package com.julianjupiter.csm.security;

import java.util.Date;

/**
 * @author Julian Jupiter
 */
public record TokenDto(String accessToken, Date expiresIn, String tokenType) {
}
