package com.example.monolithfinancialsystem.service.crud;

import com.example.monolithfinancialsystem.persistence.model.PhoneData;
import com.example.monolithfinancialsystem.persistence.repository.PhoneDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PhoneDataCrudServiceImpl extends AbstractCrudService<PhoneData, Long> implements PhoneDataCrudService {

    private final PhoneDataRepository phoneDataRepository;

    @Override
    public List<PhoneData> findAllByPhoneInAndUserIdIsNot(List<String> phones, Long userId) {
        return phoneDataRepository.findAllByPhoneInAndUserIdIsNot(phones, userId);
    }

    @Override
    public List<PhoneData> findAllByUserId(Long userId) {
        return phoneDataRepository.findAllByUserId(userId);
    }
}
