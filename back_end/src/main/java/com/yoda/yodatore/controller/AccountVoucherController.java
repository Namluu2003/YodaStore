package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.response.AccountVoucherResponse;
import com.yoda.yodatore.service.AccountVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account-voucher")
public class AccountVoucherController {

    @Autowired
    private AccountVoucherService accountVoucherService;

    // Gán voucher cho tài khoản
    @PostMapping("/assign")
    public AccountVoucher assignVoucher(@RequestParam Long accountId, @RequestParam Long voucherId) {
        return accountVoucherService.assignVoucherToAccount(accountId, voucherId);
    }

    // Hủy voucher khỏi tài khoản
    @DeleteMapping("/remove")
    public void removeVoucher(@RequestParam Long accountId, @RequestParam Long voucherId) {
        accountVoucherService.removeVoucherFromAccount(accountId, voucherId);
    }

    // Kiểm tra tài khoản có voucher không
    @GetMapping("/exists")
    public Boolean checkVoucherExists(@RequestParam Long accountId, @RequestParam Long voucherId) {
        return accountVoucherService.existsByAccountAndVoucher(accountId, voucherId);
    }

    // Lấy danh sách voucher của một tài khoản
    @GetMapping("/list/{accountId}")
    public List<AccountVoucher> getVouchers(@PathVariable Long accountId) {
        return accountVoucherService.getVouchersByAccount(accountId);
    }

    // Lấy thông tin chi tiết của một voucher thuộc tài khoản
    @GetMapping("/detail")
    public Optional<AccountVoucher> getAccountVoucherDetail(@RequestParam Long accountId, @RequestParam Long voucherId) {
        return accountVoucherService.getAccountVoucherDetail(accountId, voucherId);
    }

    // Lấy danh sách AccountVoucher có phân trang
    @PostMapping("/get-all")
    public PhanTrang<AccountVoucherResponse> getAll(@RequestBody AccountVoucherRequest request) {
        return accountVoucherService.getAll(request);
    }
}
