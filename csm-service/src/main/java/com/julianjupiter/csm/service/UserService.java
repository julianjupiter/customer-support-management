package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.CreateUserRequestDto;
import com.julianjupiter.csm.dto.UserDto;

/**
 * @author Julian Jupiter
 */
public interface UserService {
    UserDto create(CreateUserRequestDto createUserRequestDto);
}
