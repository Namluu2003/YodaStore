package com.yoda.yodatore.service;


import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import com.yoda.yodatore.infrastructure.response.VoucherResponse;
import org.springframework.http.ResponseEntity;

public interface VoucherService {

    PhanTrang<VoucherResponse> getAll(VoucherRequest request);

    Voucher getOne(Long id);
    Voucher add(VoucherRequest request);
    Voucher update(Long id, VoucherRequest voucherRequest);
    Voucher delete(Long id);
}
