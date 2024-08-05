package com.example.monolithfinancialsystem.service.processing.user.update.phone;

import com.example.model.UserPhonesDto;

/**
 * Interface representing the facade for updating user phone details.
 */
public interface UserPhoneUpdateFacade {

    /**
     * Updates the phone details of a user.
     *
     * @param userId The ID of the user whose phone details are to be updated.
     * @param authorization The authorization token for verifying the update request.
     * @param request The new phone details to be updated.
     * @return An updated UserPhonesDto containing the updated phone details.
     */
    UserPhonesDto update(Long userId, String authorization, UserPhonesDto request);

}
