package com.example.monolithfinancialsystem.service.processing.user.update.phone.validation;

import com.example.model.UserPhonesDto;
import com.example.monolithfinancialsystem.exception.BusinessValidationException;
import com.example.monolithfinancialsystem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PhoneOwnerValidation implements UserPhoneUpdateValidation {

    private final JwtUtil jwtUtil;

    @Override
    public void validate(Long userId, String authorization, UserPhonesDto request) {
        Long userIdFromToken = jwtUtil.getUserId(authorization);
        if (! Objects.equals(userId, userIdFromToken)) {
            throw new BusinessValidationException("User ID from token does not match the requested user ID");
        }
    }
}
