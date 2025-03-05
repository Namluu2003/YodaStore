package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import com.yoda.yodatore.infrastructure.response.AccountVoucherResponse;
import com.yoda.yodatore.infrastructure.response.VoucherResponse;

import java.util.List;
import java.util.Optional;

public interface AccountVoucherService {

    // Gán một voucher cho tài khoản
    AccountVoucher assignVoucherToAccount(Long accountId, Long voucherId);

    // Hủy voucher khỏi tài khoản
    void removeVoucherFromAccount(Long accountId, Long voucherId);

    // Kiểm tra tài khoản có voucher không
    Boolean existsByAccountAndVoucher(Long accountId, Long voucherId);

    // Lấy danh sách voucher của một tài khoản
    List<AccountVoucher> getVouchersByAccount(Long accountId);

    // Lấy thông tin cụ thể của một voucher thuộc tài khoản
    Optional<AccountVoucher> getAccountVoucherDetail(Long accountId, Long voucherId);

    // Lấy danh sách AccountVoucher có phân trang
    PhanTrang<AccountVoucherResponse> getAll(AccountVoucherRequest request);

}
