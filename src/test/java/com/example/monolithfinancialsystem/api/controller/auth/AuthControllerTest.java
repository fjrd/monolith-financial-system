package com.example.monolithfinancialsystem.api.controller.auth;

import com.example.model.ErrorResponse;
import com.example.model.LoginRequest;
import com.example.monolithfinancialsystem.AbstractIntegrationTest;
import com.example.monolithfinancialsystem.persistence.model.User;
import com.example.monolithfinancialsystem.persistence.repository.UserRepository;
import com.example.monolithfinancialsystem.util.JwtUtil;
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

import static com.example.monolithfinancialsystem.service.processing.auth.AuthLoginFacadeImpl.INCORRECT_CREDENTIALS_MESSAGE;

class AuthControllerTest extends AbstractIntegrationTest {

    @Autowired private UserRepository userRepository;
    @Autowired private TestUtil testUtil;
    @Autowired private JwtUtil jwtUtil;

    @BeforeEach
    public void prepareData() {
        testUtil.cleanData();
        testUtil.prepareDataForOneUser(random);
        testUtil.prepareDataForOneUser(random);
        testUtil.prepareDataForOneUser(random);
    }

    @Test
    @Transactional
    void loginAuthShouldReturnToken() {
        User user = userRepository.getFirstByOrderByIdAsc();
        String token = testUtil.requestToken(user, objectMapper, mvc);
        Assertions.assertThat(jwtUtil.validateToken(token)).isTrue();
    }

    @Test
    @Transactional
    @SneakyThrows
    void loginAuthWithIncorrectPasswordShouldReturn400() {
        User user = userRepository.getFirstByOrderByIdAsc();
        LoginRequest loginRequest = LoginRequest.builder()
                .email(user.getEmailData().get(0).getEmail())
                .password("incorrectPassword")
                .build();
        String loginResponseString = mvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        String message = objectMapper.readValue(loginResponseString, ErrorResponse.class).getMessage();
        Assertions.assertThat(message).isEqualTo(INCORRECT_CREDENTIALS_MESSAGE);
    }
}
