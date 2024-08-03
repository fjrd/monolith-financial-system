package com.example.monolithfinancialsystem.api.controller;

import com.example.api.AuthApi;
import com.example.model.Login200Response;
import com.example.model.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    @Override
    public ResponseEntity<Login200Response> login(LoginRequest loginRequest) {
        return AuthApi.super.login(loginRequest);
    }
}
