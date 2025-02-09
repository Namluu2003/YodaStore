package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.MauSac;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.response.MauSacResponse;
import com.yoda.yodatore.service.MauSacService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mausac")
public class MauSacController {

    @Autowired
    private MauSacService service;

    @GetMapping
    public PhanTrang<MauSacResponse> getAll(
            @RequestParam(required = false,defaultValue = "") String name,
            @RequestParam(required = false,defaultValue = "1") Integer page,
            @RequestParam(required = false,defaultValue = "") Boolean status){
        return service.getAll(name,page,status);
    }



    @GetMapping("/{id}")
    public MauSac getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseObject add(@RequestBody @Valid MauSac mauSac) {
        return new ResponseObject(service.add(mauSac));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid MauSac mauSac) {
        return new ResponseObject(service.update(id, mauSac));
    }


    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id){
        return new ResponseObject(service.delete(id));
    }
}
