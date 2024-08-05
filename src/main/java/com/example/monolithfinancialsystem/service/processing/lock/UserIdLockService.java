package com.example.monolithfinancialsystem.service.processing.lock;

import java.util.concurrent.locks.Lock;

public interface UserIdLockService {

    Lock getLock(Long userId);


}
