package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.BillHistory;
import com.yoda.yodatore.infrastructure.request.BillHistoryRequest;
import com.yoda.yodatore.repository.IBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillHistoryConvert {
    @Autowired
    private IBillRepository billRepository;

    public BillHistory convertRequestToEntity(BillHistoryRequest request) {
        return BillHistory.builder()
                .bill(billRepository.findById(request.getIdBill()).get())
                .note(request.getNote())
                .status(request.getStatus())
                .build();
    }
}
