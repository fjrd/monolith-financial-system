package com.example.monolithfinancialsystem.service.processing.auth;

import com.example.model.LoginRequest;
import com.example.model.LoginResponse;

/**
 * Interface representing the facade for handling user login authentication.
 */
public interface AuthLoginFacade {

    /**
     * Authenticates a user based on the provided login request.
     *
     * @param loginRequest The login request containing the user's credentials.
     * @return A LoginResponse containing the authentication result and token if successful.
     */
    LoginResponse login(LoginRequest loginRequest);

}
