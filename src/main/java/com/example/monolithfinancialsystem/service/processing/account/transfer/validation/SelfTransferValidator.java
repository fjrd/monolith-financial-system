package com.example.monolithfinancialsystem.service.processing.account.transfer.validation;

import com.example.model.TransferRequest;

import com.example.monolithfinancialsystem.exception.BusinessValidationException;
import com.example.monolithfinancialsystem.persistence.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SelfTransferValidator implements AccountTransferValidation {

    @Override
    public void validate(Account fromAccount, Account toAccount, TransferRequest request) {
        if (Objects.equals(fromAccount.getId(), toAccount.getId())) {
            throw new BusinessValidationException("Cannot transfer money to the same account");
        }
    }
}
