package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.Account;
import com.yoda.yodatore.entity.Address;
import com.yoda.yodatore.infrastructure.common.GenCode;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.AccountConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.AccountRequest;
import com.yoda.yodatore.infrastructure.response.AccountResponse;
import com.yoda.yodatore.repository.*;
import com.yoda.yodatore.service.*;
import com.yoda.yodatore.util.CloudinaryUtils;
import com.yoda.yodatore.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountConvert accountConvert;

    @Autowired
    private MailUtils mailUtils;
    @Autowired
    private CloudinaryUtils cloudinaryUtils;

    @Override
    public PhanTrang<AccountResponse> getAll(AccountRequest request) {
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSizePage());
        return new PhanTrang<>(accountRepository.getAll(request, pageable));
    }

    @Override
    public Account getOne(Long id, String roleName) {
        return accountRepository.getOne(id, roleName);
    }


    public Account delete(Long id) {
        return null;
    }
}
