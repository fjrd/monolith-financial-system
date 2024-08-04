package com.example.monolithfinancialsystem.service.crud;

import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserCrudServiceImpl extends AbstractCrudService<User, Long> implements UserCrudService {

    private final UserRepository userRepository;

    @Override
    public Page<User> findUsersBy(Specification<User> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }

}
