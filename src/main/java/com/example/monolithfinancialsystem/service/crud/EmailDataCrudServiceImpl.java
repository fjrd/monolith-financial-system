package com.example.monolithfinancialsystem.service.crud;

import com.example.monolithfinancialsystem.persistence.model.EmailData;
import com.example.monolithfinancialsystem.persistence.repository.EmailDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailDataCrudServiceImpl extends AbstractCrudService<EmailData, Long> implements EmailDataCrudService {

    private final EmailDataRepository emailDataRepository;

    @Override
    public List<EmailData> findAllByEmailInAndUserIdIsNot(List<String> emails, Long userId) {
        return emailDataRepository.findAllByEmailInAndUserIdIsNot(emails, userId);
    }

    @Override
    public List<EmailData> findAllByEmailIn(List<String> emails) {
        return emailDataRepository.findAllByEmailIn(emails);
    }

    @Override
    public List<EmailData> findAllByUserId(Long userId) {
        return emailDataRepository.findAllByUserId(userId);
    }
}
