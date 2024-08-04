package com.example.monolithfinancialsystem.service.crud;

import com.example.monolithfinancialsystem.persistence.model.PhoneData;

import java.util.List;

public interface PhoneDataCrudService extends AbstractCrudServiceInterface<PhoneData, Long> {

    List<PhoneData> findAllByPhoneInAndUserIdIsNot(List<String> phones, Long userId);

    List<PhoneData> findAllByUserId(Long userId);
}
