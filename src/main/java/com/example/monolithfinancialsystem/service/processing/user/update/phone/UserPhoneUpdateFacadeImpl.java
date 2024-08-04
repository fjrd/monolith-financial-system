package com.example.monolithfinancialsystem.service.processing.user.update.phone;

import com.example.model.UserPhonesDto;
import com.example.monolithfinancialsystem.mapper.PhoneDataMapper;
import com.example.monolithfinancialsystem.persistence.model.PhoneData;
import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.service.crud.PhoneDataCrudService;
import com.example.monolithfinancialsystem.service.crud.UserCrudService;
import com.example.monolithfinancialsystem.service.processing.user.update.phone.validation.UserPhoneUpdateValidation;
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
public class UserPhoneUpdateFacadeImpl implements UserPhoneUpdateFacade {

    private final List<UserPhoneUpdateValidation> validations;
    private final PhoneDataCrudService phoneDataCrudService;
    private final UserCrudService userCrudService;
    private final PhoneDataMapper phoneDataMapper;

    @Override
    public UserPhonesDto update(Long userId, UserPhonesDto request) {
        validations.forEach(v -> v.validate(userId, request));
        User user = userCrudService.getByIdOrThrow(userId);

        Map<String, PhoneData> existingUserPhones = user.getPhoneData().stream()
                .collect(Collectors.toMap(PhoneData::getPhone, Function.identity()));

        List<PhoneData> toSave = request.getPhones().stream()
                .filter(Predicate.not(existingUserPhones.keySet()::contains))
                .map(phone -> phoneDataMapper.mapToEntity(user, phone))
                .collect(Collectors.toList());
        phoneDataCrudService.saveAll(toSave);

        List<PhoneData> toRemove = existingUserPhones.entrySet().stream()
                .filter(Predicate.not(entry -> request.getPhones().contains(entry.getKey())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        phoneDataCrudService.deleteAll(toRemove);

        List<String> allUpdatedUserPhones = phoneDataCrudService.findAllByUserId(userId)
                .stream()
                .map(PhoneData::getPhone)
                .collect(Collectors.toList());

        return UserPhonesDto.builder()
                .phones(allUpdatedUserPhones)
                .build();
    }
}
