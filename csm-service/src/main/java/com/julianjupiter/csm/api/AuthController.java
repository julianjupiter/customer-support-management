package com.julianjupiter.csm.api;

import com.julianjupiter.csm.dto.ApiResponseDto;
import com.julianjupiter.csm.dto.LoginRequestDto;
import com.julianjupiter.csm.dto.CreateUserRequestDto;
import com.julianjupiter.csm.dto.TokenDto;
import com.julianjupiter.csm.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Julian Jupiter
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenDto login(LoginRequestDto loginRequestDto) {
        return this.authService.login(loginRequestDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto register(@RequestBody CreateUserRequestDto createUserRequestDto) {
        var createdUser = this.authService.register(createUserRequestDto);

        return ApiResponseDto.of(HttpStatus.CREATED, createdUser);
    }
}
