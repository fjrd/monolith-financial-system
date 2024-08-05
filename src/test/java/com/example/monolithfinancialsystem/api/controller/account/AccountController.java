package com.example.monolithfinancialsystem.api.controller.account;

import com.example.model.ErrorResponse;
import com.example.model.TransferRequest;
import com.example.model.TransferResponse;
import com.example.monolithfinancialsystem.AbstractIntegrationTest;
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

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static com.example.monolithfinancialsystem.service.processing.account.transfer.AccountTransferFacadeImpl.SUCCESSFUL_TRANSFER_MESSAGE;
import static com.example.monolithfinancialsystem.service.processing.account.transfer.validation.SufficientFondsValidation.INSUFFICIENT_FUNDS_ERROR_MESSAGE;

class AccountController extends AbstractIntegrationTest {

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
    void transferIsWorkingTest() {
        User userFromBeforeRequest = userRepository.getFirstByOrderByIdAsc();
        User userToBeforeRequest = userRepository.getFirstByOrderByIdDesc();

        BigDecimal transferValue = userToBeforeRequest.getAccount().getBalance().multiply(BigDecimal.valueOf(0.5));
        BigDecimal userFromSupposedAccountBalanceAfterTransfer = userFromBeforeRequest.getAccount().getBalance().subtract(transferValue);
        BigDecimal userToSupposedAccountBalanceAfterTransfer = userToBeforeRequest.getAccount().getBalance().add(transferValue);

        TransferRequest request = TransferRequest.builder()
                .toUserId(userToBeforeRequest.getId())
                .value(transferValue)
                .build();
        String responseString = mvc.perform(MockMvcRequestBuilders.post("/accounts/transfer")
                        .header("Authorization", "Bearer " + testUtil.requestToken(userFromBeforeRequest, objectMapper, mvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        TransferResponse response = objectMapper.readValue(responseString, TransferResponse.class);

        User userFromAfterRequest = userRepository.getReferenceById(userFromBeforeRequest.getId());
        User userToAfterRequest = userRepository.getReferenceById(userToBeforeRequest.getId());

        Assertions.assertThat(response.getMessage()).isEqualTo(SUCCESSFUL_TRANSFER_MESSAGE);
        Assertions.assertThat(userFromAfterRequest.getAccount().getBalance()).isEqualByComparingTo(userFromSupposedAccountBalanceAfterTransfer);
        Assertions.assertThat(userToAfterRequest.getAccount().getBalance()).isEqualByComparingTo(userToSupposedAccountBalanceAfterTransfer);
    }

    @Test
    @SneakyThrows
    @Transactional
    void transferInsufficientFundsShouldNotWork() {
        User userFromBeforeRequest = userRepository.getFirstByOrderByIdAsc();
        User userToBeforeRequest = userRepository.getFirstByOrderByIdDesc();

        BigDecimal transferValue = userToBeforeRequest.getAccount().getBalance().multiply(BigDecimal.valueOf(3));

        TransferRequest request = TransferRequest.builder()
                .toUserId(userToBeforeRequest.getId())
                .value(transferValue)
                .build();
        String responseString = mvc.perform(MockMvcRequestBuilders.post("/accounts/transfer")
                        .header("Authorization", "Bearer " + testUtil.requestToken(userFromBeforeRequest, objectMapper, mvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ErrorResponse response = objectMapper.readValue(responseString, ErrorResponse.class);
        Assertions.assertThat(response.getMessage()).isEqualTo(INSUFFICIENT_FUNDS_ERROR_MESSAGE);
    }
}
