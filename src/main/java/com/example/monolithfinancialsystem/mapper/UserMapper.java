package com.example.monolithfinancialsystem.mapper;

import com.example.model.UserDto;
import com.example.model.UserSearchResponse;
import com.example.monolithfinancialsystem.persistence.model.User;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PhoneDataMapper.class, EmailDataMapper.class, AccountMapper.class},
        imports = {Optional.class}

)
public interface UserMapper {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Mapping(target = "content", source = "userPage.content")
    UserSearchResponse mapPageToResponse(Page<User> userPage);

    @IterableMapping(qualifiedByName = "mapToUserDto")
    List<UserDto> mapUsersToUserDtos(List<User> users);

    @Named("mapToUserDto")
    @Mapping(source = "account", target = "account")
    @Mapping(source = "phoneData", target = "phones", qualifiedByName = "mapPhoneToString")
    @Mapping(source = "emailData", target = "emails", qualifiedByName = "mapEmailToString")
    @Mapping(target = "dateOfBirth", expression = "java(Optional.ofNullable(user.getDateOfBirth()).map(d -> d.format(FORMATTER)).orElse(null))")
    UserDto mapToUserDto(User user);

}
