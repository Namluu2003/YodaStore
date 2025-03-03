package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import com.yoda.yodatore.infrastructure.response.VoucherResponse;

public interface VoucherService {
    PhanTrang<VoucherResponse> getAll(VoucherRequest request);
    Voucher getOne(Long id);

    Voucher add(VoucherRequest voucher);

    Voucher update(Long id, VoucherRequest voucher);

    String delete(Long id);

    boolean isVoucherCodeExists(String code);

    void updateStatus();

    void createScheduledVoucher();
}
