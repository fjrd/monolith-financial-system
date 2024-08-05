package com.example.monolithfinancialsystem.service.processing.user.update.email.validation;

import com.example.model.UserEmailsDto;

/**
 * Functional interface for validating the user email update request.
 */
@FunctionalInterface
public interface UserEmailUpdateValidation {

    /**
     * Validates the email update request for a user.
     *
     * @param userId The ID of the user whose email update request is to be validated.
     * @param authorization The authorization token for verifying the update request.
     * @param request The email update request to be validated.
     */
    void validate(Long userId, String authorization, UserEmailsDto request);

}
