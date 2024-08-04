package com.example.monolithfinancialsystem.service.processing.user.update.email.validation;

import com.example.model.UserEmailsDto;
import com.example.monolithfinancialsystem.exception.BusinessValidationException;
import com.example.monolithfinancialsystem.persistence.model.EmailData;
import com.example.monolithfinancialsystem.service.crud.EmailDataCrudService;
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
public class UniqueEmailValidation implements UserEmailUpdateValidation {

    private final EmailDataCrudService emailDataCrudService;

    @Override
    public void validate(Long userId, UserEmailsDto request) {
        Optional.ofNullable(request)
                .map(r -> emailDataCrudService.findAllByEmailInAndUserIdIsNot(r.getEmails(), userId))
                .filter(Predicate.not(List::isEmpty))
                .ifPresent(emails -> {
                    throw new BusinessValidationException(String.format("Emails must be unique! List of passed non-unique emails: %s",
                            emails.stream().map(EmailData::getEmail).collect(Collectors.toList())
                    ));
                });
    }
}
