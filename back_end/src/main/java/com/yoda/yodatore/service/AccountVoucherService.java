package com.yoda.yodatore.service;


import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.response.AccountVoucherResponse;

public interface AccountVoucherService {

    PhanTrang<AccountVoucherResponse> getAll(AccountVoucherRequest request);

    AccountVoucher getOne(Long id);
    AccountVoucher add(AccountVoucherRequest request);
    AccountVoucher update(Long id, AccountVoucherRequest request);
    AccountVoucher delete(Long id);

}
