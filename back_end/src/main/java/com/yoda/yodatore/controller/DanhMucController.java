package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.DanhMuc;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.response.DanhMucResponse;
import com.yoda.yodatore.infrastructure.response.DeResponse;
import com.yoda.yodatore.service.DanhMucService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/danhmuc")
public class DanhMucController {
    @Autowired
    private DanhMucService danhMucService;

    @GetMapping
    public PhanTrang<DanhMucResponse> getAll(@RequestParam(required = false, defaultValue = "") String name,
                                             @RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(required = false) Boolean status) {
        return danhMucService.getAll(name, page, status);
    }

    @GetMapping("/{id}")
    public DanhMuc getOne(@PathVariable Long id) {
        return danhMucService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid DanhMuc danhMuc) {
        return new ResponseObject(danhMucService.add(danhMuc));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid DanhMuc danhMuc) {
        return new ResponseObject(danhMucService.update(id, danhMuc));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(danhMucService.delete(id));

    }

}
