package com.example.monolithfinancialsystem.service.crud;

import com.example.monolithfinancialsystem.persistence.model.Account;
import com.example.monolithfinancialsystem.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountCrudServiceImpl extends AbstractCrudService<Account, Long> implements AccountCrudService {

    private final AccountRepository accountRepository;

    @Override
    public Account getBlockingByUserId(Long userId) {
        return accountRepository.getBlockingByUserId(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBalance(Long userId, BigDecimal interestRate, BigDecimal maxMultiplier) {
        Optional.ofNullable(userId)
                .map(accountRepository::getBlockingByUserId)
                .filter(account -> isMaximumInterestHasBeenReached(maxMultiplier).test(account))
                .ifPresent(account -> {
                    BigDecimal maxBalanceValue = account.getInitialDeposit().multiply(maxMultiplier);
                    BigDecimal updatedBalanceWithInterest = account.getBalance().multiply(interestRate);
                    BigDecimal newBalanceValue = updatedBalanceWithInterest.compareTo(maxBalanceValue) > 0 ? maxBalanceValue : updatedBalanceWithInterest;
                    account.setBalance(newBalanceValue);
                    accountRepository.save(account);
                });
    }

    private Predicate<Account> isMaximumInterestHasBeenReached(BigDecimal maxMultiplier) {
        return account -> {
            BigDecimal maxBalanceValue = account.getInitialDeposit().multiply(maxMultiplier);
            return account.getBalance().compareTo(maxBalanceValue) < 0;
        };
    }
}
