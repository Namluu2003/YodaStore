package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.Bill;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.bill.BillSearchRequest;
import com.yoda.yodatore.infrastructure.response.BillResponse;

public interface BillService {
    PhanTrang<BillResponse> getAll(BillSearchRequest request);
    Bill getOne(Long id);
    Bill create();
}
