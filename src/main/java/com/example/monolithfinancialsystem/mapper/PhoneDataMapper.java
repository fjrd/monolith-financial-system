package com.example.monolithfinancialsystem.mapper;

import com.example.monolithfinancialsystem.persistence.model.PhoneData;
import com.example.monolithfinancialsystem.persistence.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PhoneDataMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user", target = "user")
    @Mapping(source = "phone", target = "phone")
    PhoneData mapToEntity(User user, String phone);

    @Named("mapPhoneToString")
    default String mapPhoneToString(PhoneData phoneData) {
        return phoneData.getPhone();
    }
}
