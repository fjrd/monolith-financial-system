package com.example.monolithfinancialsystem.service.scheduling;

import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.service.crud.AccountCrudService;
import com.example.monolithfinancialsystem.service.crud.UserCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountBalanceUpdater {

    private final AccountCrudService accountCrudService;
    private final UserCrudService userCrudService;

    @Value("${scheduling.account-interest.rate}")
    private BigDecimal interestRate;

    @Value("${scheduling.account-interest.max-multiplier}")
    private BigDecimal maxMultiplier;

    @Scheduled(fixedDelayString = "${scheduling.account-interest.fixed-delay-ms}")
    public void updateBalances() {
        userCrudService.findAll()
                .stream()
                .map(User::getId)
                .forEach(userId -> accountCrudService.updateBalance(userId, interestRate, maxMultiplier));
    }
}