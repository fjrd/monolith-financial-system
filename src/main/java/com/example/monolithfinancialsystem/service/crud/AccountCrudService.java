package com.example.monolithfinancialsystem.service.crud;

import com.example.monolithfinancialsystem.persistence.model.Account;

import java.math.BigDecimal;

public interface AccountCrudService extends AbstractCrudServiceInterface<Account, Long> {

    Account getBlockingByUserId(Long userId);

    void updateBalance(Long userId, BigDecimal interestRate, BigDecimal maxMultiplier);

}
