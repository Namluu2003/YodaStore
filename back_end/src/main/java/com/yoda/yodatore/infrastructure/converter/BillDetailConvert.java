package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.Bill;
import com.yoda.yodatore.entity.BillDetail;
import com.yoda.yodatore.entity.SanPhamChiTiet;
import com.yoda.yodatore.infrastructure.request.BillDetailRequest;
import com.yoda.yodatore.repository.IBillRepository;
import com.yoda.yodatore.repository.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillDetailConvert {
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private SanPhamChiTietRepository shoeDetailRepository;

    public BillDetail convertRequestToEntity(BillDetailRequest request) {
        SanPhamChiTiet shoeDetail = shoeDetailRepository.findByCode(request.getShoeDetail());
        Bill bill = billRepository.findById(request.getBill()).get();
        return BillDetail.builder()
                .shoeDetail(shoeDetail)
                .bill(bill)
                .price(shoeDetail.getPrice())
                .quantity(request.getQuantity())
                .build();
    }

    public BillDetail convertRequestToEntity(BillDetail entity, BillDetailRequest request) {
        SanPhamChiTiet shoeDetail = shoeDetailRepository.findByCode(request.getShoeDetail());
        Bill bill = billRepository.findById(request.getBill()).get();

        entity.setShoeDetail(shoeDetail);
        entity.setBill(bill);
        entity.setPrice(request.getPrice());
        entity.setQuantity(entity.getQuantity() + request.getQuantity());
        return entity;
    }
}
