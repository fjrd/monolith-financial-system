package com.example.monolithfinancialsystem.persistence.specification;

import com.example.model.UserSearchParams;
import com.example.monolithfinancialsystem.persistence.model.User;
import org.springframework.data.jpa.domain.Specification;

/**
 * Interface for building JPA specifications for querying User entities.
 */
public interface UserJpaSpecificationService {

    /**
     * Builds a JPA specification for querying users based on the given search parameters.
     *
     * @param userSearchParams The search parameters for finding users.
     * @return A Specification<User> for querying users.
     */
    Specification<User> buildSpecBy(UserSearchParams userSearchParams);

}
