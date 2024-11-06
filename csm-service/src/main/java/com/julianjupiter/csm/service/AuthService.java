package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.LoginRequestDto;
import com.julianjupiter.csm.dto.TokenDto;

/**
 * @author Julian Jupiter
 */
public interface AuthService {
    TokenDto login(LoginRequestDto loginRequestDto);
}
