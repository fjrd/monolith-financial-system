package com.example.monolithfinancialsystem.api.controller;

import com.example.api.UsersApi;
import com.example.model.UserEmailsDto;
import com.example.model.UserPhonesDto;
import com.example.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    @Override
    public ResponseEntity<UserEmailsDto> createOrUpdateUserEmails(Long id, UserEmailsDto userEmailsDto) {
        return UsersApi.super.createOrUpdateUserEmails(id, userEmailsDto);
    }

    @Override
    public ResponseEntity<UserPhonesDto> createOrUpdateUserPhones(Long id, UserPhonesDto userPhonesDto) {
        return UsersApi.super.createOrUpdateUserPhones(id, userPhonesDto);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long id) {
        return UsersApi.super.getUserById(id);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers(LocalDate dateOfBirth, String phone, String name, String email, Long page, Long size) {
        return UsersApi.super.getUsers(dateOfBirth, phone, name, email, page, size);
    }
}
