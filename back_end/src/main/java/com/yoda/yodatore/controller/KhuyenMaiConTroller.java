package com.yoda.yodatore.controller;

import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.KhuyenMaiRequest;
import com.yoda.yodatore.infrastructure.response.KhuyenMaiResponse;
import com.yoda.yodatore.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/promotion")
public class KhuyenMaiConTroller {
    @Autowired
    private KhuyenMaiService service;

    @GetMapping
    public PhanTrang getAll(KhuyenMaiRequest request) {
        return service.getAll(request);
    }


    @GetMapping("/{id}")
    public KhuyenMaiResponse getOne(@PathVariable Long id){
        return service.getOne(id);
    }


    @GetMapping("/list-shoe-id")
    public List<Long> getListIdShoePromotion(@RequestParam(required = false) Long idPromotion){
        return service.getListIdShoePromotion(idPromotion);
    }

    @GetMapping("/list-shoe-detail-id")
    public List<Long> getListIdShoeDetailPromotion(@RequestParam(required = false) Long idPromotion){
        return service.getListIdShoeDetailInPromotion(idPromotion);
    }

    @PostMapping
    public ResponseObject create(@RequestBody KhuyenMaiRequest request){
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id,@RequestBody KhuyenMaiRequest request){
        return new ResponseObject(service.update(id,request));
    }

    @DeleteMapping("/delete-all-promotion-detail/{id}")
    public void deleteAllPromotionDetailByPromotion(@PathVariable Long id){
        service.deleteAll(id);
    }

    @PutMapping("/update/end-date/{id}")
    public ResponseObject updateEndDate( @PathVariable Long id) {
        return new ResponseObject(service.updateEndDate(id));
    }
}
