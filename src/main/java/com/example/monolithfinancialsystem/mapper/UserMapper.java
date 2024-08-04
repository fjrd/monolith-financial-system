package com.example.monolithfinancialsystem.mapper;

import com.example.model.UserDto;
import com.example.model.UserSearchResponse;
import com.example.monolithfinancialsystem.persistence.model.User;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PhoneDataMapper.class, EmailDataMapper.class, AccountMapper.class}
)
public interface UserMapper {

    @Mapping(target = "content", source = "userPage.content")
    UserSearchResponse mapPageToResponse(Page<User> userPage);

    @IterableMapping(qualifiedByName = "toUserDto")
    List<UserDto> mapUsersToUserDtos(List<User> users);

    @Named("toUserDto")
    @Mapping(source = "account", target = "account")
    @Mapping(source = "phoneData", target = "phones", qualifiedByName = "mapPhoneToString")
    @Mapping(source = "emailData", target = "emails", qualifiedByName = "mapEmailToString")
    UserDto toUserDto(User user);
}
