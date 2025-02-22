package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.Account;
import com.yoda.yodatore.infrastructure.request.AccountRequest;
import com.yoda.yodatore.repository.AccountRepository;
import com.yoda.yodatore.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountConvert {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    public Account convertRequestToEntity(AccountRequest request){
        return Account.builder()
                .username(request.getUsername())
                .name(request.getName())
                .gender(request.getGender())
                .email(request.getEmail())
                .cccd(request.getCccd())
                .phoneNumber(request.getPhoneNumber())
                .birthday(request.getBirthday())
                .build();
    }

}

