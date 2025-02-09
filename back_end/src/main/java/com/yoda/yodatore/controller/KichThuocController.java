package com.yoda.yodatore.controller;


import com.yoda.yodatore.entity.KichThuoc;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.response.KichThuocResponse;
import com.yoda.yodatore.service.KichThuocService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kichthuoc")
public class KichThuocController {
    @Autowired
    private KichThuocService kichThuocService;

    @GetMapping
    public PhanTrang<KichThuocResponse> getAll(@RequestParam(required = false, defaultValue = "") String name,
                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false) Boolean status) {
        return kichThuocService.getAll(name, page, status);
    }

    @GetMapping("/{id}")
    public KichThuoc getOne(@PathVariable Long id) {
        return kichThuocService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid KichThuoc kichThuoc) {
        return new ResponseObject(kichThuocService.add(kichThuoc));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid KichThuoc kichThuoc) {
        return new ResponseObject(kichThuocService.update(id, kichThuoc));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(kichThuocService.delete(id));
    }
}
