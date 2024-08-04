package com.example.monolithfinancialsystem.mapper;

import com.example.model.AccountDTO;
import com.example.monolithfinancialsystem.persistence.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    AccountDTO mapToDto(Account account);

}
