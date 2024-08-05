package com.example.monolithfinancialsystem.api.controller.user;

import com.example.model.*;
import com.example.monolithfinancialsystem.AbstractIntegrationTest;
import com.example.monolithfinancialsystem.persistence.model.EmailData;
import com.example.monolithfinancialsystem.persistence.model.PhoneData;
import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.persistence.repository.UserRepository;
import com.example.monolithfinancialsystem.util.TestUtil;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class UserControllerTest extends AbstractIntegrationTest {

    @Autowired private TestUtil testUtil;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    public void prepareData() {
        testUtil.cleanData();
        testUtil.prepareDataForOneUser(random);
        testUtil.prepareDataForOneUser(random);
        testUtil.prepareDataForOneUser(random);
    }

    @Test
    @SneakyThrows
    @Transactional
    void createOrUpdateUserEmails() {
        User user = userRepository.getFirstByOrderByIdAsc();
        String newEmail = "newEmail@qweq.com";
        List<String> emailsBeforeRequest = user.getEmailData().stream().map(EmailData::getEmail).collect(Collectors.toList());
        String oldEmailThatDoeNotPresentInRequest = emailsBeforeRequest.get(1);
        String oldEmailThatIsPresentInRequest = emailsBeforeRequest.get(0);

        List<String> emailsRequest = Stream.of(oldEmailThatIsPresentInRequest, newEmail).collect(Collectors.toList());
        UserEmailsDto request = UserEmailsDto.builder().emails(emailsRequest).build();
        String responseString = mvc.perform(MockMvcRequestBuilders.put(String.format("/users/%d/emails", user.getId()))
                        .header("Authorization", "Bearer " + testUtil.requestToken(user, objectMapper, mvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        UserEmailsDto emailsResponse = objectMapper.readValue(responseString, UserEmailsDto.class);

        Assertions.assertThat(emailsBeforeRequest).doesNotContain(newEmail);
        Assertions.assertThat(emailsResponse.getEmails()).contains(oldEmailThatIsPresentInRequest);
        Assertions.assertThat(emailsResponse.getEmails()).doesNotContain(oldEmailThatDoeNotPresentInRequest);
        Assertions.assertThat(emailsResponse.getEmails()).containsExactlyInAnyOrder(emailsRequest.toArray(String[]::new));

    }

    @Test
    @SneakyThrows
    @Transactional
    void createOrUpdateUserPhones() {
        User user = userRepository.getFirstByOrderByIdAsc();
        String newPhone = "78918521413";
        List<String> phonesBeforeRequest = user.getPhoneData().stream().map(PhoneData::getPhone).collect(Collectors.toList());
        String oldPhoneThatIsPresentInRequest = phonesBeforeRequest.get(0);
        String oldPhoneThatDoesNotPresentInRequest = phonesBeforeRequest.get(1);

        List<String> phonesRequest = Stream.of(oldPhoneThatIsPresentInRequest, newPhone).collect(Collectors.toList());
        UserPhonesDto request = UserPhonesDto.builder().phones(phonesRequest).build();
        String responseString = mvc.perform(MockMvcRequestBuilders.put(String.format("/users/%d/phones", user.getId()))
                        .header("Authorization", "Bearer " + testUtil.requestToken(user, objectMapper, mvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        UserPhonesDto phonesResponse = objectMapper.readValue(responseString, UserPhonesDto.class);

        Assertions.assertThat(phonesBeforeRequest).doesNotContain(newPhone);
        Assertions.assertThat(phonesResponse.getPhones()).contains(oldPhoneThatIsPresentInRequest);
        Assertions.assertThat(phonesResponse.getPhones()).doesNotContain(oldPhoneThatDoesNotPresentInRequest);
        Assertions.assertThat(phonesResponse.getPhones()).containsExactlyInAnyOrder(phonesRequest.toArray(String[]::new));
    }

    @Test
    @SneakyThrows
    @Transactional
    void findUsersShouldReturnCorrectData() {
        User user = userRepository.getFirstByOrderByIdAsc();
        String responseString = mvc.perform(MockMvcRequestBuilders.get("/users")
                        .param("name", user.getName().substring(2))
                        .param("dateOfBirth", user.getDateOfBirth().toString())
                        .param("phone", user.getPhoneData().get(0).getPhone())
                        .param("email", user.getEmailData().get(0).getEmail())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<UserDto> userDtoList = objectMapper.readValue(responseString, UserSearchResponse.class).getContent();
        Assertions.assertThat(userDtoList).size().isEqualTo(1);
        UserDto userDto = userDtoList.stream().findFirst().get();
        assertUserDto(userDto, user);
    }

    @Test
    @SneakyThrows
    @Transactional
    void getUserByIdShouldReturnCorrectData() {
        User user = userRepository.getFirstByOrderByIdAsc();
        String responseString = mvc.perform(MockMvcRequestBuilders.get(String.format("/users/%d", user.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(responseString, UserByIdResponse.class).getContent();
        assertUserDto(userDto, user);
    }

    private void assertUserDto(UserDto userDto, User user) {
        Assertions.assertThat(userDto.getId()).isEqualTo(user.getId());
        Assertions.assertThat(userDto.getName()).isEqualTo(user.getName());
        Assertions.assertThat(userDto.getDateOfBirth()).isEqualTo(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        Assertions.assertThat(userDto.getAccount().getId()).isEqualTo(user.getAccount().getId());
        Assertions.assertThat(userDto.getAccount().getBalance()).isEqualTo(user.getAccount().getBalance());
        Assertions.assertThat(userDto.getEmails()).containsExactlyInAnyOrder(user.getEmailData().stream().map(EmailData::getEmail).toArray(String[]::new));
        Assertions.assertThat(userDto.getPhones()).containsExactlyInAnyOrder(user.getPhoneData().stream().map(PhoneData::getPhone).toArray(String[]::new));
    }
}
