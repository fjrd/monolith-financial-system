package com.example.monolithfinancialsystem.service.processing.user.update.email.validation;

import com.example.model.UserEmailsDto;
import com.example.monolithfinancialsystem.exception.BusinessValidationException;
import com.example.monolithfinancialsystem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailOwnerValidation implements UserEmailUpdateValidation {

    private final JwtUtil jwtUtil;

    @Override
    public void validate(Long userId, String authorization, UserEmailsDto request) {
        Long userIdFromToken = jwtUtil.getUserId(authorization);
        if (! Objects.equals(userId, userIdFromToken)) {
            throw new BusinessValidationException("User ID from token does not match the requested user ID");
        }
    }
}
