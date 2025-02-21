package com.yoda.yodatore.controller;


import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import com.yoda.yodatore.infrastructure.response.VoucherResponse;
import com.yoda.yodatore.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public PhanTrang<VoucherResponse> getAll(VoucherRequest request) {
        return voucherService.getAll(request);
    }

    @GetMapping("/{id}")
    public Voucher getOne(@PathVariable Long id) {
        return voucherService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid VoucherRequest request) {
        return new ResponseObject(voucherService.add(request));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid VoucherRequest request) {
        return new ResponseObject(voucherService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(voucherService.delete(id));

    }

}
