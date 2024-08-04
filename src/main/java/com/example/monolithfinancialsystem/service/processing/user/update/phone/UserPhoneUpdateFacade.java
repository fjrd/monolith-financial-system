package com.example.monolithfinancialsystem.service.processing.user.update.phone;

import com.example.model.UserPhonesDto;

public interface UserPhoneUpdateFacade {

    UserPhonesDto update(Long userId, UserPhonesDto request);

}
