package com.example.monolithfinancialsystem.persistence.repository;

import com.example.monolithfinancialsystem.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
