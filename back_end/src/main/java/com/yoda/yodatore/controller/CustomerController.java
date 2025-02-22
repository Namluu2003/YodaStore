package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.Account;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.AccountRequest;
import com.yoda.yodatore.infrastructure.response.AccountResponse;
import com.yoda.yodatore.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private static String ROLE = "Khách hàng";
    @Autowired
    private AccountService accountService;

    @GetMapping
    public PhanTrang<AccountResponse> getAll(AccountRequest request) {
        request.setRoleName(ROLE);
        return accountService.getAll(request);
    }


}
