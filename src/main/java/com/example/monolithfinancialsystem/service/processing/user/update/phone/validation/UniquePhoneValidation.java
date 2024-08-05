package com.example.monolithfinancialsystem.service.processing.user.update.phone.validation;

import com.example.model.UserPhonesDto;
import com.example.monolithfinancialsystem.exception.BusinessValidationException;
import com.example.monolithfinancialsystem.persistence.model.PhoneData;
import com.example.monolithfinancialsystem.service.crud.PhoneDataCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UniquePhoneValidation implements UserPhoneUpdateValidation {

    private final PhoneDataCrudService phoneDataCrudService;

    @Override
    public void validate(Long userId, String authorization, UserPhonesDto request) {
        Optional.ofNullable(request)
                .map(r -> phoneDataCrudService.findAllByPhoneInAndUserIdIsNot(r.getPhones(), userId))
                .filter(Predicate.not(List::isEmpty))
                .ifPresent(phones -> {
                    throw new BusinessValidationException(String.format("Phones must be unique! List of passed non-unique phones: %s",
                            phones.stream().map(PhoneData::getPhone).collect(Collectors.toList())
                    ));
                });
    }
}
