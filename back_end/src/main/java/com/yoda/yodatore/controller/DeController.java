package com.yoda.yodatore.controller;

import com.yoda.yodatore.entity.De;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.response.DeResponse;
import com.yoda.yodatore.service.DeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/de")
public class DeController {
    @Autowired
    private DeService deService;

    @GetMapping
    public PhanTrang<DeResponse> getAll(@RequestParam(required = false, defaultValue = "") String name,
                                        @RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false) Boolean status) {
        return deService.getAll(name, page, status);
    }

    @GetMapping("/{id}")
    public De getOne(@PathVariable Long id) {
        return deService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid De de) {
        return new ResponseObject(deService.add(de));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid De de) {
        return new ResponseObject(deService.update(id, de));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(deService.delete(id));

    }
}
