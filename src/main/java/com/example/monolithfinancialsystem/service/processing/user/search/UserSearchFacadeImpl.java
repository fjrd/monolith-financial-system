package com.example.monolithfinancialsystem.service.processing.user.search;

import com.example.model.Pageable;
import com.example.model.UserByIdResponse;
import com.example.model.UserSearchParams;
import com.example.model.UserSearchResponse;
import com.example.monolithfinancialsystem.mapper.UserMapper;
import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.persistence.specification.UserJpaSpecificationService;
import com.example.monolithfinancialsystem.service.crud.UserCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserSearchFacadeImpl implements UserSearchFacade {

    private final UserCrudService userCrudService;
    private final UserJpaSpecificationService userJpaSpecificationService;
    private final UserMapper userMapper;

    @Override
    public UserSearchResponse findUsersBy(Pageable pageable, UserSearchParams userSearchParams) {
        Specification<User> specification = userJpaSpecificationService.buildSpecBy(userSearchParams);
        PageRequest pageRequest = PageRequest.of(pageable.getPage(), pageable.getSize());
        Page<User> userPage = userCrudService.findUsersBy(specification, pageRequest);
        UserSearchResponse response = userMapper.mapPageToResponse(userPage);
        return response;
    }

    @Override
    public UserByIdResponse getUserById(Long id) {
        return Optional.ofNullable(id)
                .map(userCrudService::getByIdOrThrow)
                .map(userMapper::mapToUserDto)
                .map(dto -> UserByIdResponse.builder().content(dto).build())
                .orElseThrow();
    }
}
