package com.example.monolithfinancialsystem.service.processing.user.update.email.validation;

import com.example.model.UserEmailsDto;

@FunctionalInterface
public interface UserEmailUpdateValidation {

    void validate(Long userId, UserEmailsDto request);

}
