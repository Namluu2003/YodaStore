package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.SanPhamRequest;
import com.yoda.yodatore.infrastructure.response.SanPhamResponse;
import com.yoda.yodatore.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sanpham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping
    public PhanTrang<SanPhamResponse> getAll(SanPhamRequest request) {
        return sanPhamService.getAll(request);
    }

    @GetMapping("/{id}")
    public SanPham getOne(@PathVariable Long id) {
        return sanPhamService.getOne(id);
    }
}
