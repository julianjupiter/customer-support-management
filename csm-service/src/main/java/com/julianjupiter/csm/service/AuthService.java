package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.CreateUserRequestDto;
import com.julianjupiter.csm.dto.LoginRequestDto;
import com.julianjupiter.csm.dto.TokenDto;
import com.julianjupiter.csm.dto.UserDto;

/**
 * @author Julian Jupiter
 */
public interface AuthService {
    TokenDto login(LoginRequestDto loginRequestDto);

    UserDto register(CreateUserRequestDto createUserRequestDto);
}
