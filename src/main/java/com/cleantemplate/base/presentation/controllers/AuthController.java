package com.cleantemplate.base.presentation.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleantemplate.base.application.dto.LoginDTO;
import com.cleantemplate.base.application.dto.TokenResponse;
import com.cleantemplate.base.application.usecases.LoginUseCase;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public TokenResponse login(
        @RequestBody LoginDTO dto
    ) {

        String token = loginUseCase.executar(dto);

        return new TokenResponse(token);
    }
}
