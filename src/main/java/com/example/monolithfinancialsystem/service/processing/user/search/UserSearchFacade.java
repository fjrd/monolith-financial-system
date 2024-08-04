package com.example.monolithfinancialsystem.service.processing.user.search;

import com.example.model.Pageable;
import com.example.model.UserSearchParams;
import com.example.model.UserSearchResponse;

public interface UserSearchFacade {

    UserSearchResponse findUsersBy(Pageable pageable, UserSearchParams userSearchParams);

}
