package com.example.monolithfinancialsystem.service.crud;

import com.example.monolithfinancialsystem.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserCrudService extends AbstractCrudServiceInterface<User, Long> {

    Page<User> findUsersBy(Specification<User> specification, Pageable pageable);
}
