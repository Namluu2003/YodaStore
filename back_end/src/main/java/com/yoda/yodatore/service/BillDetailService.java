package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.BillDetail;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.BillDetailRequest;
import com.yoda.yodatore.infrastructure.response.BillDetailResponse;

public interface BillDetailService {
    PhanTrang<BillDetailResponse> getAll(BillDetailRequest request);

    BillDetail getOne(Long id);
    BillDetail create(BillDetailRequest request);
    BillDetail update(Long id,BillDetailRequest request);
    BillDetail delete(Long id);

    BillDetail updateQuantity(Long id, Integer newQuantity);
}
