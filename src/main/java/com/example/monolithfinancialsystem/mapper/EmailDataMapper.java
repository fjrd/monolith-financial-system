package com.example.monolithfinancialsystem.mapper;

import com.example.monolithfinancialsystem.persistence.model.EmailData;
import com.example.monolithfinancialsystem.persistence.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmailDataMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "email", target = "email")
    EmailData mapToEntity(User user, String email);

}