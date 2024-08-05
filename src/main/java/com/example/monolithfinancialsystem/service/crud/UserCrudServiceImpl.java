package com.example.monolithfinancialsystem.service.crud;

import com.example.model.LoginRequest;
import com.example.monolithfinancialsystem.exception.BusinessValidationException;
import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

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

    @Override
    public User getByLoginRequestOrThrow(LoginRequest loginRequest) {
        Optional<User> userByEmail = Optional.ofNullable(loginRequest.getEmail())
                .flatMap(userRepository::getByEmailDataEmail);
        Optional<User> userByPhone = Optional.ofNullable(loginRequest.getPhone())
                .flatMap(userRepository::getByPhoneDataPhone);
        User user = Stream.of(userByEmail, userByPhone)
                .filter(Optional::isPresent)
                .reduce((u1, u2) -> {
                    throw new BusinessValidationException("Multiple users found by provided email and phone");
                })
                .map(Optional::get)
                .orElseThrow(() -> new BusinessValidationException("No user found by provided email or phone"));
        return user;
    }
}
