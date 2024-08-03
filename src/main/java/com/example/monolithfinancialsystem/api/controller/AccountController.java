package com.example.monolithfinancialsystem.api.controller;

import com.example.api.AccountApi;
import com.example.model.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    @Override
    public ResponseEntity<Void> transfer(TransferRequest transferRequest) {
        return AccountApi.super.transfer(transferRequest);
    }
}
