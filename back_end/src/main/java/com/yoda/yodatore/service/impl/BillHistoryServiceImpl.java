package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.converter.BillHistoryConvert;
import com.yoda.yodatore.infrastructure.request.BillHistoryRequest;
import com.yoda.yodatore.infrastructure.response.BillHistoryResponse;
import com.yoda.yodatore.repository.IBillHistoryRepository;
import com.yoda.yodatore.service.BillHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillHistoryServiceImpl implements BillHistoryService {
    @Autowired
    private IBillHistoryRepository repository;
    @Autowired
    private BillHistoryConvert billHistoryConvert;

    @Override
    public List<BillHistoryResponse> getByBill(Long idBill) {
        return repository.getByBill(idBill);
    }

    @Override
    public ResponseObject create(BillHistoryRequest request) {
        return new ResponseObject(billHistoryConvert.convertRequestToEntity(request));
    }
}
