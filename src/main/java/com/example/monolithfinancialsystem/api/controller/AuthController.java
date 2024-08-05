package com.example.monolithfinancialsystem.api.controller;

import com.example.api.AuthApi;
import com.example.model.LoginRequest;
import com.example.model.LoginResponse;
import com.example.monolithfinancialsystem.service.processing.auth.AuthLoginFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthLoginFacade authLoginFacade;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        LoginResponse response = authLoginFacade.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
