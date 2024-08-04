package com.example.monolithfinancialsystem.service.processing.user.update.email;

import com.example.model.UserEmailsDto;

import com.example.monolithfinancialsystem.mapper.EmailDataMapper;
import com.example.monolithfinancialsystem.persistence.model.EmailData;
import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.service.crud.EmailDataCrudService;
import com.example.monolithfinancialsystem.service.crud.UserCrudService;
import com.example.monolithfinancialsystem.service.processing.user.update.email.validation.UserEmailUpdateValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserEmailUpdateFacadeImpl implements UserEmailUpdateFacade {

    private final List<UserEmailUpdateValidation> validations;
    private final EmailDataCrudService emailDataCrudService;
    private final UserCrudService userCrudService;
    private final EmailDataMapper emailDataMapper;

    @Override
    public UserEmailsDto updateUserEmails(Long userId, UserEmailsDto request) {
        validations.forEach(v -> v.validate(userId, request));
        User user = userCrudService.getByIdOrThrow(userId);

        Map<String, EmailData> existingUserEmails = user.getEmailData().stream()
                .collect(Collectors.toMap(EmailData::getEmail, Function.identity()));

        List<EmailData> toSave = request.getEmails().stream()
                .filter(Predicate.not(existingUserEmails.keySet()::contains))
                .map(email -> emailDataMapper.mapToEntity(user, email))
                .collect(Collectors.toList());
        emailDataCrudService.saveAll(toSave);

        List<EmailData> toRemove = existingUserEmails.entrySet().stream()
                .filter(Predicate.not(entry -> request.getEmails().contains(entry.getKey())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        emailDataCrudService.deleteAll(toRemove);

        List<String> allUpdatedUserEmails = emailDataCrudService.findAllByUserId(userId)
                .stream()
                .map(EmailData::getEmail)
                .collect(Collectors.toList());

        return UserEmailsDto.builder()
                .emails(allUpdatedUserEmails)
                .build();
    }
}
