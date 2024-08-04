package com.example.monolithfinancialsystem.service.processing.user.update.phone.validation;

import com.example.model.UserPhonesDto;

@FunctionalInterface
public interface UserPhoneUpdateValidation {

    void validate(Long userId, UserPhonesDto request);

}
