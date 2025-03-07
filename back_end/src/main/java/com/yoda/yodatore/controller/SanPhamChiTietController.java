package com.yoda.yodatore.controller;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.FindShoeDetailRequest;
import com.yoda.yodatore.infrastructure.request.SanPhamChiTietRequest;
import com.yoda.yodatore.infrastructure.request.SanPhamChiTietUpdateRequest;
import com.yoda.yodatore.service.SanPhamChiTietService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yoda.yodatore.entity.SanPhamChiTiet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shoe-detail")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @GetMapping
    public PhanTrang getAll(FindShoeDetailRequest request) {
        return sanPhamChiTietService.getAll(request);
    }

    @GetMapping("/{id}")
    public SanPhamChiTiet getOne(@PathVariable Long id) {
        return sanPhamChiTietService.getOne(id);
    }

    @GetMapping("/get-one/{id}")
    public ResponseObject getOneShoeDetail(@PathVariable Long id) {
        return new ResponseObject(sanPhamChiTietService.getOneShoeDetail(id));
    }


    @GetMapping("/find-min-max-price")
    public Map<String, BigDecimal> findMinAndMaxPrice() {
        return sanPhamChiTietService.findMinAndMaxPrice();
    }

    @PostMapping
    public ResponseObject create(@RequestBody List<SanPhamChiTietRequest> list) {
        list.forEach(request -> System.out.println(request));
        return new ResponseObject(sanPhamChiTietService.create(list));
    }


    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid SanPhamChiTietUpdateRequest request) {
        return new ResponseObject(sanPhamChiTietService.update(id, request));
    }




}
