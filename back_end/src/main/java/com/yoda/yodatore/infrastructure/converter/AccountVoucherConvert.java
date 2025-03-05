package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.Account;
import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.repository.AccountRepository;
import com.yoda.yodatore.repository.VoucherRepository;
import com.yoda.yodatore.repository.AccountVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountVoucherConvert {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private AccountVoucherRepository accountVoucherRepository;

    public AccountVoucher convertRequestToEntity(AccountVoucherRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));

        Voucher voucher = voucherRepository.findById(request.getVoucherId())
                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại!"));

        return AccountVoucher.builder()
                .account(account)
                .voucher(voucher)
                .build();
    }

    public AccountVoucher convertRequestToEntity(Long id, AccountVoucherRequest request) {
        AccountVoucher accountVoucher = accountVoucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy AccountVoucher!"));

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));

        Voucher voucher = voucherRepository.findById(request.getVoucherId())
                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại!"));

        accountVoucher.setAccount(account);
        accountVoucher.setVoucher(voucher);

        return accountVoucher;
    }
}
