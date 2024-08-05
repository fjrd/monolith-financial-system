package com.example.monolithfinancialsystem.service.processing.auth;

import com.example.model.LoginRequest;
import com.example.model.LoginResponse;
import com.example.monolithfinancialsystem.exception.BusinessValidationException;
import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.service.crud.UserCrudService;
import com.example.monolithfinancialsystem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthLoginFacadeImpl implements AuthLoginFacade {

    private final UserCrudService userCrudService;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userCrudService.getByLoginRequestOrThrow(loginRequest);
        if (! Objects.equals(user.getPassword(), loginRequest.getPassword())) {
            throw new BusinessValidationException("Incorrect email, phone or password");
        }
        String token = jwtUtil.generateToken(user.getId());
        return LoginResponse.builder().token(token).build();
    }
}
