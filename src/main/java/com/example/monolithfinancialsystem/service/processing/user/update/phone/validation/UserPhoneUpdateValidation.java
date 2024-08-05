package com.example.monolithfinancialsystem.service.processing.user.update.phone.validation;

import com.example.model.UserPhonesDto;

/**
 * Functional interface for validating the user phone update request.
 */
@FunctionalInterface
public interface UserPhoneUpdateValidation {

    /**
     * Validates the phone update request for a user.
     *
     * @param userId The ID of the user whose phone update request is to be validated.
     * @param authorization The authorization token for verifying the update request.
     * @param request The phone update request to be validated.
     */
    void validate(Long userId, String authorization, UserPhonesDto request);

}
