package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.Account;
import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import com.yoda.yodatore.repository.AccountRepository;
import com.yoda.yodatore.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountVoucherConvert {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    public AccountVoucher addconvertRequest(AccountVoucherRequest request){

        Account account = accountRepository.findById(request.getAccount()).get();
        Voucher voucher = voucherRepository.findById(request.getVoucher()).get();

        AccountVoucher accountVoucher = AccountVoucher.builder()
                .account(account)
                .voucher(voucher)
                .build();
        return accountVoucher;
    }

    public AccountVoucher convertRequestToEntity(AccountVoucher entity, AccountVoucherRequest request){

        Account account = accountRepository.findById(request.getAccount()).get();
        Voucher voucher = voucherRepository.findById(request.getVoucher()).get();

        entity.setAccount(account);
        entity.setVoucher(voucher);
        return entity;
    }

}
