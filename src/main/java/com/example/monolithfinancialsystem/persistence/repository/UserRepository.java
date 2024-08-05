package com.example.monolithfinancialsystem.persistence.repository;

import com.example.monolithfinancialsystem.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


    Optional<User> getByEmailDataEmail(String email);

    Optional<User> getByPhoneDataPhone(String phone);
}
