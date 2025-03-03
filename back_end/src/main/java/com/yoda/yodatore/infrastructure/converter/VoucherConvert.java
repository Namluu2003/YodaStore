package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import com.yoda.yodatore.repository.VoucherRepository;
import com.yoda.yodatore.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Component
public class VoucherConvert {

    @Autowired
    private VoucherRepository voucherRepository;
    public Voucher converRequestToEntity(VoucherRequest request){
        return Voucher.builder()
                .code(request.getCode())
                .name(request.getName())
                .quantity(request.getQuantity())
                .percentReduce(Float.valueOf(request.getPercentReduce()))
                .minBillValue(new BigDecimal(request.getMinBillValue().toString()))
                .maxBillValue(new BigDecimal(request.getMinBillValue().toString()))
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }
    public Voucher convertRequestToEntity(Long id, VoucherRequest request){
        Voucher voucher = voucherRepository.findById(id).get();
        voucher.setName(request.getName());
        voucher.setQuantity(request.getQuantity());
        voucher.setPercentReduce(Float.valueOf(request.getPercentReduce()));
        voucher.setMinBillValue(new BigDecimal(request.getMinBillValue().toString()));
        voucher.setMaxBillValue(new BigDecimal(request.getMinBillValue().toString()));
        voucher.setCode(request.getCode());
        voucher.setStartDate(request.getStartDate());
        voucher.setEndDate(request.getEndDate());
        return voucher;
    }
}
