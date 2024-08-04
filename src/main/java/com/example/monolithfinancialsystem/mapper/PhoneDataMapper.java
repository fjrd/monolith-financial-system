package com.example.monolithfinancialsystem.mapper;

import com.example.monolithfinancialsystem.persistence.model.PhoneData;
import com.example.monolithfinancialsystem.persistence.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)

public interface PhoneDataMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "phone", target = "phone")
    PhoneData mapToEntity(User user, String phone);

}
