package com.yoda.yodatore.service.impl;


import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.AccountVoucherConvert;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.response.AccountVoucherResponse;
import com.yoda.yodatore.repository.AccountVoucherRepository;
import com.yoda.yodatore.service.AccountVoucherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountVoucherImpl implements AccountVoucherService {

    @Autowired
    private AccountVoucherRepository repository;

    @Autowired
    private AccountVoucherConvert convert;

    @Override
    public PhanTrang<AccountVoucherResponse> getAll(AccountVoucherRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PhanTrang<>(repository.getAllAccountVoucher(request, pageable));
    }

    @Override
    public AccountVoucher getOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public AccountVoucher add(AccountVoucherRequest request) {

        AccountVoucher accountVoucher = convert.addconvertRequest(request);
        return repository.save(accountVoucher);
    }

    @Override
    public AccountVoucher update(Long id, AccountVoucherRequest request) {

        AccountVoucher entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("❌ Không tìm thấy AccountVoucher với ID: " + id));

        return repository.save(convert.convertRequestToEntity(entity, request));
    }

    @Override
    public AccountVoucher delete(Long id) {
        AccountVoucher accountVoucher = repository.findById(id).get();
        accountVoucher.setDeleted(!accountVoucher.getDeleted());
        return repository.save(accountVoucher);
    }
}
