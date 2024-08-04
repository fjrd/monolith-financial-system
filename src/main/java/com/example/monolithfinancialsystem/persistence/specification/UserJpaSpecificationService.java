package com.example.monolithfinancialsystem.persistence.specification;

import com.example.model.UserSearchParams;
import com.example.monolithfinancialsystem.persistence.model.User;
import org.springframework.data.jpa.domain.Specification;

public interface UserJpaSpecificationService {

    Specification<User> buildSpecBy(UserSearchParams userSearchParams);

}
