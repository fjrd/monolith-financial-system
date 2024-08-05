package com.example.monolithfinancialsystem.service.processing.user.update.email;

import com.example.model.UserEmailsDto;

/**
 * Interface representing the facade for updating user email details.
 */
public interface UserEmailUpdateFacade {

    /**
     * Updates the email details of a user.
     *
     * @param userId The ID of the user whose email details are to be updated.
     * @param authorization The authorization token for verifying the update request.
     * @param userEmailsDto The new email details to be updated.
     * @return An updated UserEmailsDto containing the updated email details.
     */
    UserEmailsDto update(Long userId, String authorization, UserEmailsDto userEmailsDto);

}
