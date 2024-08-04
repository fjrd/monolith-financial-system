package com.example.monolithfinancialsystem.service.processing.account.transfer.validation;

import com.example.model.TransferRequest;
import com.example.monolithfinancialsystem.persistence.model.Account;

@FunctionalInterface
public interface AccountTransferValidation {

    void validate(Account fromAccount, Account toAccount, TransferRequest request);

}
