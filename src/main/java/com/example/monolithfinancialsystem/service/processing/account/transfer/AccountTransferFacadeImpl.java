package com.example.monolithfinancialsystem.service.processing.account.transfer;

import com.example.model.TransferRequest;
import com.example.model.TransferResponse;
import com.example.monolithfinancialsystem.persistence.model.Account;
import com.example.monolithfinancialsystem.service.crud.AccountCrudService;
import com.example.monolithfinancialsystem.service.processing.account.transfer.validation.AccountTransferValidation;
import com.example.monolithfinancialsystem.service.processing.lock.UserIdLockService;
import com.example.monolithfinancialsystem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountTransferFacadeImpl implements AccountTransferFacade {

    public static final String SUCCESSFUL_TRANSFER_MESSAGE = "Transfer is completed";

    private final List<AccountTransferValidation> validations;
    private final AccountCrudService accountCrudService;
    private final UserIdLockService userIdLockService;
    private final JwtUtil jwtUtil;

    @Override
    public TransferResponse transfer(String authorization, TransferRequest request) {
        Long fromUserId = jwtUtil.getUserId(authorization);
        Long toUserId = request.getToUserId();

        log.info("Initiating transfer from user {} to user {}. Transfer request: {}", fromUserId, toUserId, request);

        Lock lock1 = userIdLockService.getLock(Math.min(fromUserId, toUserId));
        Lock lock2 = userIdLockService.getLock(Math.max(fromUserId, toUserId));

        lock1.lock();
        try {
            lock2.lock();
            try {
                log.info("Locks acquired for users {} and {}", fromUserId, toUserId);

                Account fromAccount = accountCrudService.getBlockingByUserId(fromUserId);
                Account toAccount = accountCrudService.getBlockingByUserId(toUserId);
                validations.forEach(v -> v.validate(fromAccount, toAccount, request));

                fromAccount.setBalance(fromAccount.getBalance().subtract(request.getValue()));
                toAccount.setBalance(toAccount.getBalance().add(request.getValue()));
                accountCrudService.save(fromAccount);
                accountCrudService.save(toAccount);

                log.info("Transfer is successful");
                return TransferResponse.builder().message(SUCCESSFUL_TRANSFER_MESSAGE).build();
            }
            finally {
                lock2.unlock();
                log.info("Lock1 released for user {}", toUserId);
            }
        }
        finally {
            lock1.unlock();
            log.info("Lock2 released for user {}", fromUserId);
        }
    }
}
