package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.Images;
import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.SanPhamRequest;

import com.yoda.yodatore.infrastructure.response.SanPhamResponse;
import com.yoda.yodatore.repository.AnhRepository;
import com.yoda.yodatore.service.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shoe")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private AnhRepository anhRepository;

    @GetMapping
    public PhanTrang<SanPhamResponse> getAll(SanPhamRequest request) {
        return sanPhamService.getAll(request);
    }



    @GetMapping("/{id}")
    public SanPham getOne(@PathVariable Long id) {
        return sanPhamService.getOne(id);
    }
    @GetMapping("/{id}/images")
    public List<String> getImagesBySanPhamId(@PathVariable Long id) {
        return anhRepository.findBySanPhamId(id).stream()
                .map(Images::getName)
                .collect(Collectors.toList());
    }


    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseObject create(@ModelAttribute @Valid SanPhamRequest request) {
        return new ResponseObject(sanPhamService.create(request));
    }


    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseObject update(@PathVariable Long id, @ModelAttribute @Valid SanPhamRequest request) {
        return new ResponseObject(sanPhamService.update(id, request));
    }




    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(sanPhamService.delete(id));
    }
}
