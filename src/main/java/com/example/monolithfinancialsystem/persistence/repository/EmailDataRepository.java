package com.example.monolithfinancialsystem.persistence.repository;

import com.example.monolithfinancialsystem.persistence.model.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

    List<EmailData> findAllByUserId(Long userId);

    List<EmailData> findAllByEmailIn(List<String> emails);

    List<EmailData> findAllByEmailInAndUserIdIsNot(List<String> emails, Long userId);

}
