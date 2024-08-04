package com.example.monolithfinancialsystem.service.processing.user.update.email;

import com.example.model.UserEmailsDto;

public interface UserEmailUpdateFacade {

    UserEmailsDto update(Long userId, UserEmailsDto userEmailsDto);

}
