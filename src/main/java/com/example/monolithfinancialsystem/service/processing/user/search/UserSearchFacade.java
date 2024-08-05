package com.example.monolithfinancialsystem.service.processing.user.search;

import com.example.model.Pageable;
import com.example.model.UserByIdResponse;
import com.example.model.UserSearchParams;
import com.example.model.UserSearchResponse;

/**
 * Interface representing the facade for searching user details.
 */
public interface UserSearchFacade {

    /**
     * Finds users based on the given search parameters and pagination information.
     *
     * @param pageable The pagination information.
     * @param userSearchParams The search parameters for finding users.
     * @return A UserSearchResponse containing the search results.
     */
    UserSearchResponse findUsersBy(Pageable pageable, UserSearchParams userSearchParams);

    /**
     * Retrieves user details by user ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return A UserByIdResponse containing the user details.
     */
    UserByIdResponse getUserById(Long id);
}
