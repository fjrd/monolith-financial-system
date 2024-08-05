package com.example.monolithfinancialsystem.util;

import com.example.model.LoginRequest;
import com.example.model.LoginResponse;
import com.example.monolithfinancialsystem.persistence.model.Account;
import com.example.monolithfinancialsystem.persistence.model.EmailData;
import com.example.monolithfinancialsystem.persistence.model.PhoneData;
import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.persistence.repository.AccountRepository;
import com.example.monolithfinancialsystem.persistence.repository.EmailDataRepository;
import com.example.monolithfinancialsystem.persistence.repository.PhoneDataRepository;
import com.example.monolithfinancialsystem.persistence.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@Transactional
@RequiredArgsConstructor
public class TestUtil {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final EmailDataRepository emailDataRepository;

    public void prepareDataForOneUser(EnhancedRandom random) {
        User user = random.nextObject(User.class);

        BigDecimal initialBalance1 = random.nextObject(BigDecimal.class);
        Account account = random.nextObject(Account.class).setUser(user)
                .setBalance(initialBalance1)
                .setInitialDeposit(initialBalance1);
        user.setAccount(account);

        PhoneData phoneData1 = random.nextObject(PhoneData.class).setUser(user).setPhone(String.valueOf(random.nextObject(Long.class)));
        PhoneData phoneData2 = random.nextObject(PhoneData.class).setUser(user).setPhone(String.valueOf(random.nextObject(Long.class)));
        user.getPhoneData().add(phoneData1);
        user.getPhoneData().add(phoneData2);

        EmailData emailData1 = random.nextObject(EmailData.class).setUser(user);
        EmailData emailData2 = random.nextObject(EmailData.class).setUser(user);
        user.getEmailData().add(emailData1);
        user.getEmailData().add(emailData2);

        userRepository.save(user);
        accountRepository.save(account);
        phoneDataRepository.save(phoneData1);
        phoneDataRepository.save(phoneData2);
        emailDataRepository.save(emailData1);
        emailDataRepository.save(emailData2);
    }

    public void cleanData() {
        emailDataRepository.deleteAll();
        phoneDataRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @SneakyThrows
    public String requestToken(User user, ObjectMapper objectMapper, MockMvc mvc) {
        LoginRequest loginRequest = LoginRequest.builder()
                .email(user.getEmailData().get(0).getEmail())
                .password(user.getPassword())
                .build();
        String loginResponseString = mvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(loginResponseString, LoginResponse.class).getToken();
    }
}
