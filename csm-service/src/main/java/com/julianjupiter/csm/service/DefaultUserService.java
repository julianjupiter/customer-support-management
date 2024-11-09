package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.CreateUserRequestDto;
import com.julianjupiter.csm.dto.UserDto;
import com.julianjupiter.csm.exception.ApplicationException;
import com.julianjupiter.csm.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author Julian Jupiter
 */
@Service
class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto create(CreateUserRequestDto createUserRequestDto) {
        this.validateRegistration(createUserRequestDto);

        return null;
    }

    private void validateRegistration(CreateUserRequestDto createUserRequestDto) {
        // Validate if username exists
        var username = createUserRequestDto.username();
        if (this.userRepository.existsByUsername(username)) {
            throw new ApplicationException(HttpStatus.UNPROCESSABLE_ENTITY, "User already exists");
        }

        // More validations to do
    }
}
