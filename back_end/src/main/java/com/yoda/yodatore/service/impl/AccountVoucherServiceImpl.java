package com.yoda.yodatore.service.impl;


import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.AccountVoucherConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.response.AccountVoucherResponse;
import com.yoda.yodatore.repository.AccountVoucherRepository;
import com.yoda.yodatore.service.AccountVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountVoucherServiceImpl implements AccountVoucherService {

    @Autowired
    private AccountVoucherRepository accountVoucherRepository;

    @Autowired
    private AccountVoucherConvert accountVoucherConvert;

    @Override
    public AccountVoucher assignVoucherToAccount(Long accountId, Long voucherId) {
        if (existsByAccountAndVoucher(accountId, voucherId)) {
            throw new NgoaiLe("Tài khoản đã có voucher này.");
        }
        // Tạo đối tượng request từ accountId và voucherId
        AccountVoucherRequest request = new AccountVoucherRequest();
        request.setAccountId(accountId);
        request.setVoucherId(voucherId);

        // Chuyển đổi request thành entity
        AccountVoucher accountVoucher = accountVoucherConvert.convertRequestToEntity(request);
        return accountVoucherRepository.save(accountVoucher);
    }

    @Override
    public void removeVoucherFromAccount(Long accountId, Long voucherId) {
        Optional<AccountVoucher> accountVoucher = accountVoucherRepository.findByAccount_IdAndVoucher_Id(accountId, voucherId);
        accountVoucher.ifPresent(accountVoucherRepository::delete);
    }

    @Override
    public Boolean existsByAccountAndVoucher(Long accountId, Long voucherId) {
        return accountVoucherRepository.existsByAccount_IdAndVoucher_Id(accountId, voucherId);
    }

    @Override
    public List<AccountVoucher> getVouchersByAccount(Long accountId) {
        return accountVoucherRepository.findByAccount_Id(accountId);
    }

    @Override
    public Optional<AccountVoucher> getAccountVoucherDetail(Long accountId, Long voucherId) {
        return accountVoucherRepository.findByAccount_IdAndVoucher_Id(accountId, voucherId);
    }

    @Override
    public PhanTrang<AccountVoucherResponse> getAll(AccountVoucherRequest request) {
        return new PhanTrang<>(accountVoucherRepository.getAll(request, PageRequest.of(request.getPage()-1 > 0 ? request.getPage()-1 : 0, request.getSizePage())));
    }

}
