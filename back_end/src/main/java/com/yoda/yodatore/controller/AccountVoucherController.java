package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.response.AccountVoucherResponse;
import com.yoda.yodatore.service.AccountVoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accountvoucher")
public class AccountVoucherController {

    @Autowired
    private AccountVoucherService service;

    @GetMapping
    public PhanTrang<AccountVoucherResponse> getAll(AccountVoucherRequest request){
        return service.getAll(request);
    }

    @GetMapping("/{id}")
    public AccountVoucher getOne(@PathVariable Long id){
        return service.getOne(id);
    }

    @PostMapping("")
    public ResponseObject add(@RequestBody @Valid AccountVoucherRequest request) {
        return new ResponseObject(service.add(request));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid AccountVoucherRequest request) {
        return new ResponseObject(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id){
        return new ResponseObject(service.delete(id));
    }

}
