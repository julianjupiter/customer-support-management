package com.julianjupiter.csm.controller;

import com.julianjupiter.csm.dto.LoginRequestDto;
import com.julianjupiter.csm.dto.TokenDto;
import com.julianjupiter.csm.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Julian Jupiter
 */
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return this.authService.login(loginRequestDto);
    }
}
