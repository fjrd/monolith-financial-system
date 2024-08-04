package com.example.monolithfinancialsystem.service.crud;

import com.example.monolithfinancialsystem.persistence.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountCrudServiceImpl extends AbstractCrudService<Account, Long> implements AccountCrudService {

}
