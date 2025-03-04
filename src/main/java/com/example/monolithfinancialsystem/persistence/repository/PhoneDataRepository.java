package com.example.monolithfinancialsystem.persistence.repository;

import com.example.monolithfinancialsystem.persistence.model.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    List<PhoneData> findAllByPhoneInAndUserIdIsNot(List<String> phones, Long userId);

    List<PhoneData> findAllByUserId(Long userId);
}
