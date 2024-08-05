package com.example.monolithfinancialsystem.service.processing.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@RequiredArgsConstructor
public class UserIdLockServiceImpl implements UserIdLockService {

    private final ConcurrentHashMap<Long, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    @Override
    public Lock getLock(Long userId) {
        return lockMap.computeIfAbsent(userId, k -> new ReentrantLock());
    }
}