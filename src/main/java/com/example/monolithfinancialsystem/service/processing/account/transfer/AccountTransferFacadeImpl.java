package com.example.monolithfinancialsystem.service.processing.account.transfer;

import com.example.model.TransferRequest;
import com.example.model.TransferResponse;
import com.example.monolithfinancialsystem.persistence.model.Account;
import com.example.monolithfinancialsystem.service.crud.AccountCrudService;
import com.example.monolithfinancialsystem.service.processing.account.transfer.validation.AccountTransferValidation;
import com.example.monolithfinancialsystem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountTransferFacadeImpl implements AccountTransferFacade {

    private static final String SUCCESSFUL_TRANSFER_MESSAGE = "Transfer is completed";

    private final ConcurrentHashMap<Long, Lock> lockMap = new ConcurrentHashMap<>();

    private final List<AccountTransferValidation> validations;
    private final AccountCrudService accountCrudService;
    private final JwtUtil jwtUtil;

    @Override
    public TransferResponse transfer(String authorization, TransferRequest request) {
        Long fromUserId = jwtUtil.getUserId(authorization);
        Long toUserId = request.getToUserId();

        Lock lock1 = lockMap.computeIfAbsent(Math.min(fromUserId, toUserId), k -> new ReentrantLock());
        Lock lock2 = lockMap.computeIfAbsent(Math.max(fromUserId, toUserId), k -> new ReentrantLock());

        lock1.lock();
        try {
            lock2.lock();
            try {
                Account fromAccount = accountCrudService.getBlockingByUserId(fromUserId);
                Account toAccount = accountCrudService.getBlockingByUserId(toUserId);
                validations.forEach(v -> v.validate(fromAccount, toAccount, request));

                fromAccount.setBalance(fromAccount.getBalance().subtract(request.getValue()));
                toAccount.setBalance(toAccount.getBalance().add(request.getValue()));
                accountCrudService.save(fromAccount);
                accountCrudService.save(toAccount);
                return TransferResponse.builder().message(SUCCESSFUL_TRANSFER_MESSAGE).build();
            }
            finally {
                lock2.unlock();
            }
        }
        finally {
            lock1.unlock();
        }
    }
}
