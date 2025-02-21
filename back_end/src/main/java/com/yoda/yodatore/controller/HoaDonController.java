package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.HoaDonRequest;
import com.yoda.yodatore.infrastructure.response.HoaDonResponse;
import com.yoda.yodatore.service.HoaDonService;
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
@RequestMapping("/api/hoadon")
public class HoaDonController {

    @Autowired
    private HoaDonService service;

    @GetMapping
    public PhanTrang<HoaDonResponse> getAll(HoaDonRequest request){
        return service.getAll(request);
    }

    @GetMapping("/{id}")
    public HoaDon getOne(@PathVariable Long id){
        return service.getOne(id);
    }

    @PostMapping("/add")
    public ResponseObject add(@RequestBody @Valid HoaDonRequest request) {
        return new ResponseObject(service.add(request));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid HoaDonRequest request) {
        return new ResponseObject(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id){
        return new ResponseObject(service.delete(id));
    }
}
