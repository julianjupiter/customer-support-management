package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.CreateUserRequestDto;
import com.julianjupiter.csm.dto.LoginRequestDto;
import com.julianjupiter.csm.dto.TokenDto;
import com.julianjupiter.csm.dto.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Julian Jupiter
 */
@Service
class DefaultAuthService implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public DefaultAuthService(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public TokenDto login(LoginRequestDto loginRequestDto) {
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(
                loginRequestDto.username(),
                loginRequestDto.password()
        );

        Authentication authenticationResult = this.authenticationManager.authenticate(authenticationRequest);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);

        return this.jwtService.generateToken(authenticationResult);
    }

    @Override
    public UserDto register(CreateUserRequestDto createUserRequestDto) {
        return this.userService.create(createUserRequestDto);
    }
}
