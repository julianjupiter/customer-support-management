package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.CreateUserRequestDto;
import com.julianjupiter.csm.dto.ErrorMessageDto;
import com.julianjupiter.csm.dto.UserDto;
import com.julianjupiter.csm.entity.RoleType;
import com.julianjupiter.csm.exception.ApplicationException;
import com.julianjupiter.csm.mapper.UserMapper;
import com.julianjupiter.csm.repository.RoleRepository;
import com.julianjupiter.csm.repository.UserRepository;
import com.julianjupiter.csm.util.Constants;
import com.julianjupiter.csm.util.ValidatorUtil;
import jakarta.validation.ValidatorFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Julian Jupiter
 */
@Service
@Transactional
class DefaultUserService implements UserService {
    private final MessageSource messageSource;
    private final Validator validator;
    private final ValidatorFactory validatorFactory;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    DefaultUserService(MessageSource messageSource,
                       Validator validator,
                       ValidatorFactory validatorFactory,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserMapper userMapper) {
        this.messageSource = messageSource;
        this.validator = validator;
        this.validatorFactory = validatorFactory;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto create(CreateUserRequestDto createUserRequestDto) {
        this.validateRegistration(createUserRequestDto);
        var roleAgent = this.roleRepository.getReferenceById(RoleType.ROLE_AGENT.id());
        var newUser = this.userMapper.map(createUserRequestDto, roleAgent);
        var createdUser = this.userRepository.save(newUser);

        return this.userMapper.map(createdUser);
    }

    private void validateRegistration(CreateUserRequestDto createUserRequestDto) {
        var errorMessages = new ArrayList<ErrorMessageDto>();

        List<ErrorMessageDto> usernameErrors = ValidatorUtil.validate(this.validatorFactory, createUserRequestDto, "createUserRequestDto", "username", this.messageSource);
        if (usernameErrors.isEmpty()) {
            var username = createUserRequestDto.username();
            if (this.userRepository.existsByUsername(username)) {
                var message = this.messageSource.getMessage(Constants.MessageCode.USERNAME_ALREADY_EXISTS, new Object[]{}, Locale.ENGLISH);
                errorMessages.add(new ErrorMessageDto("username", message));
            }
        }

        List<ErrorMessageDto> emailErrors = ValidatorUtil.validate(this.validatorFactory, createUserRequestDto, "createUserRequestDto", "email", this.messageSource);
        if (emailErrors.isEmpty()) {
            var email = createUserRequestDto.email();
            if (this.userRepository.existsByEmail(email)) {
                var message = this.messageSource.getMessage(Constants.MessageCode.EMAIL_ALREADY_EXISTS, new Object[]{}, Locale.ENGLISH);
                errorMessages.add(new ErrorMessageDto("email", message));
            }
        }

        List<ErrorMessageDto> otherErrors = ValidatorUtil.validate(this.validator, createUserRequestDto, "createUserRequestDto", this.messageSource);
        if (!otherErrors.isEmpty()) {
            errorMessages.addAll(otherErrors);
        }

        if (!errorMessages.isEmpty()) {
            throw new ApplicationException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid registration")
                    .errorMessages(errorMessages);
        }
    }
}
