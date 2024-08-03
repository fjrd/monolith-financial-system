package com.example.monolithfinancialsystem.persistence.repository;

import com.example.monolithfinancialsystem.persistence.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
