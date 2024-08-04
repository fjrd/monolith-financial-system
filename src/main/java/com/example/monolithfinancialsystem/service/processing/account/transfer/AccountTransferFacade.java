package com.example.monolithfinancialsystem.service.processing.account.transfer;

import com.example.model.TransferRequest;
import com.example.model.TransferResponse;

public interface AccountTransferFacade {

    TransferResponse transfer(TransferRequest transferRequest);

}
