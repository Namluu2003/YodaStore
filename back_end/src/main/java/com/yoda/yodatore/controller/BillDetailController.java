package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.BillDetail;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.BillDetailRequest;
import com.yoda.yodatore.infrastructure.response.BillDetailResponse;
import com.yoda.yodatore.service.BillDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bill-detail")
public class BillDetailController {
    @Autowired
    private BillDetailService billDetailService;

    @GetMapping
    public PhanTrang<BillDetailResponse> getAll(BillDetailRequest request) {
        return billDetailService.getAll(request);
    }

    @GetMapping("/{id}")
    public BillDetail getOne(@PathVariable Long id) {
        return billDetailService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid BillDetailRequest request) {
        return new ResponseObject(billDetailService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseObject create(@PathVariable Long id, @RequestBody @Valid BillDetailRequest request) {
        return new ResponseObject(billDetailService.update(id, request));
    }

    @GetMapping("/update-quantity/{id}")
    public ResponseObject updateQuantity(@PathVariable Long id, @RequestParam(required = false, defaultValue = "0") Integer newQuantity) {
        return new ResponseObject(billDetailService.updateQuantity(id, newQuantity));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id){
        return new ResponseObject(billDetailService.delete(id));
    }
}
