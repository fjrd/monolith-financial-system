package com.example.monolithfinancialsystem.service.crud;

import com.example.monolithfinancialsystem.persistence.model.EmailData;

import java.util.List;

public interface EmailDataCrudService extends AbstractCrudServiceInterface<EmailData, Long> {

    List<EmailData> findAllByEmailInAndUserIdIsNot(List<String> emails, Long userId);

    List<EmailData> findAllByEmailIn(List<String> emails);

    List<EmailData> findAllByUserId(Long userId);

}
