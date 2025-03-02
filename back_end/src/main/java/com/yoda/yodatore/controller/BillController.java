package com.yoda.yodatore.controller;


import com.yoda.yodatore.entity.Bill;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.bill.BillSearchRequest;
import com.yoda.yodatore.infrastructure.response.BillResponse;
import com.yoda.yodatore.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @GetMapping
    public PhanTrang getAll(BillSearchRequest request) {
        return billService.getAll(request);
    }

    @GetMapping("/{id}")
    public Bill getOne(@PathVariable Long id) {
        return billService.getOne(id);
    }

    @PostMapping
    public ResponseObject create() {
        return new ResponseObject(billService.create());
    }
}
