package com.example.monolithfinancialsystem.api.controller;

import com.example.api.UsersApi;
import com.example.model.*;
import com.example.monolithfinancialsystem.service.processing.user.search.UserSearchFacade;
import com.example.monolithfinancialsystem.service.processing.user.update.email.UserEmailUpdateFacade;
import com.example.monolithfinancialsystem.service.processing.user.update.phone.UserPhoneUpdateFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserEmailUpdateFacade userEmailUpdateFacade;
    private final UserPhoneUpdateFacade userPhoneUpdateFacade;
    private final UserSearchFacade userSearchFacade;

    @Override
    public ResponseEntity<UserEmailsDto> createOrUpdateUserEmails(Long userId, UserEmailsDto userEmailsDto) {
        UserEmailsDto response = userEmailUpdateFacade.update(userId, userEmailsDto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserPhonesDto> createOrUpdateUserPhones(Long userId, UserPhonesDto userPhonesDto) {
        UserPhonesDto response = userPhoneUpdateFacade.update(userId, userPhonesDto);
        return ResponseEntity.ok(response);    }

    @Override
    public ResponseEntity<UserByIdResponse> getUserById(Long id) {
        return UsersApi.super.getUserById(id);
    }

    @Override
    public ResponseEntity<UserSearchResponse> findUsersBy(Pageable pageable, UserSearchParams userSearchParams) {
        UserSearchResponse response = userSearchFacade.findUsersBy(pageable, userSearchParams);
        return ResponseEntity.ok(response);
    }
}
