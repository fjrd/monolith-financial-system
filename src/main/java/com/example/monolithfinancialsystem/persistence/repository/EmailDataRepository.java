package com.example.monolithfinancialsystem.persistence.repository;

import com.example.monolithfinancialsystem.persistence.model.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

}
