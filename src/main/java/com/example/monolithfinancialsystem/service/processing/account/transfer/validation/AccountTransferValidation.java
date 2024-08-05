package com.example.monolithfinancialsystem.service.processing.account.transfer.validation;

import com.example.model.TransferRequest;
import com.example.monolithfinancialsystem.persistence.model.Account;

/**
 * Functional interface for validating an account transfer request.
 */
@FunctionalInterface
public interface AccountTransferValidation {

    /**
     * Validates the transfer request between two accounts.
     *
     * @param fromAccount The account from which the funds are to be transferred.
     * @param toAccount The account to which the funds are to be transferred.
     * @param request The transfer request to be validated.
     */
    void validate(Account fromAccount, Account toAccount, TransferRequest request);

}
