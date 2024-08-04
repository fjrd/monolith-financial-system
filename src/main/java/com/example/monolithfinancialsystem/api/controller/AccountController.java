package com.example.monolithfinancialsystem.api.controller;

import com.example.api.AccountApi;
import com.example.model.TransferRequest;
import com.example.model.TransferResponse;
import com.example.monolithfinancialsystem.service.processing.account.transfer.AccountTransferFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountTransferFacade accountTransferFacade;

    @Override
    public ResponseEntity<TransferResponse> transfer(TransferRequest transferRequest) {
        TransferResponse response = accountTransferFacade.transfer(transferRequest);
        return ResponseEntity.ok(response);
    }
}
