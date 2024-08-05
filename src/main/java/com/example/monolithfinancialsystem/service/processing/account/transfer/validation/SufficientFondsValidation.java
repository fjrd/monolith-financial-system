package com.example.monolithfinancialsystem.service.processing.account.transfer.validation;

import com.example.model.TransferRequest;
import com.example.monolithfinancialsystem.exception.BusinessValidationException;
import com.example.monolithfinancialsystem.persistence.model.Account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SufficientFondsValidation implements AccountTransferValidation {

    public static final String INSUFFICIENT_FUNDS_ERROR_MESSAGE = "Insufficient funds in the account";

    @Override
    public void validate(Account fromAccount, Account toAccount, TransferRequest request) {
        if (fromAccount.getBalance().compareTo(request.getValue()) < 0) {
            throw new BusinessValidationException(INSUFFICIENT_FUNDS_ERROR_MESSAGE);
        }
    }
}
