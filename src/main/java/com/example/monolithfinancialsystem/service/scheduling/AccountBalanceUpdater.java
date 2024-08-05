package com.example.monolithfinancialsystem.service.scheduling;

import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.service.crud.AccountCrudService;
import com.example.monolithfinancialsystem.service.crud.UserCrudService;
import com.example.monolithfinancialsystem.service.processing.lock.UserIdLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduling.account-interest.enabled", havingValue = "true", matchIfMissing = true)
public class AccountBalanceUpdater {

    private final AccountCrudService accountCrudService;
    private final UserIdLockService userIdLockService;
    private final UserCrudService userCrudService;

    @Value("${scheduling.account-interest.rate}")
    private BigDecimal interestRate;

    @Value("${scheduling.account-interest.max-multiplier}")
    private BigDecimal maxMultiplier;

    @Scheduled(fixedDelayString = "${scheduling.account-interest.fixed-delay-ms}")
    public void updateBalances() {
        log.info("Starting scheduled balance update");
        userCrudService.findAll()
                .stream()
                .map(User::getId)
                .forEach(userId -> {
                    Lock lock = userIdLockService.getLock(userId);
                    lock.lock();
                    log.info("Lock acquired for user {}", userId);
                    try {
                        accountCrudService.updateBalance(userId, interestRate, maxMultiplier);
                        log.info("Balance updated for user {}", userId);
                    } finally {
                        lock.unlock();
                        log.info("Lock released for user {}", userId);
                    }
                });
    }
}