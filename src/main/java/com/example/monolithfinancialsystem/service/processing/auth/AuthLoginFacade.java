package com.example.monolithfinancialsystem.service.processing.auth;

import com.example.model.LoginRequest;
import com.example.model.LoginResponse;

public interface AuthLoginFacade {

    LoginResponse login(LoginRequest loginRequest);

}
