package com.yoda.yodatore.service.impl;


import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.VoucherConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import com.yoda.yodatore.infrastructure.response.VoucherResponse;
import com.yoda.yodatore.repository.VoucherRepository;
import com.yoda.yodatore.service.VoucherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherImpl implements VoucherService {

    @Autowired
    private VoucherRepository repository;

    @Autowired
    private VoucherConvert voucherConvert;

    @Override
    public PhanTrang<VoucherResponse> getAll(VoucherRequest request) {
        return new PhanTrang<>(repository.getAllVoucher(request, PageRequest.of(request.getPage() - 1, 5)));
    }

    @Override
    public Voucher getOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Voucher add(VoucherRequest request) {

        if (repository.existsByCodeIgnoreCase(request.getCode())) {
            throw new NgoaiLe("Voucher /'" + request.getCode() + "/' đã tồn tại!");
        }
        Voucher voucher = voucherConvert.addconvertRequest(request);
        return repository.save(voucher);
    }

    @Override
    public Voucher update(Long id, VoucherRequest request) {

        Voucher code = repository.findById(id).get();
        if (repository.existsByCodeIgnoreCase(request.getCode())) {
            if (code.getCode().equals(request.getCode())){
                return repository.save(voucherConvert.convertRequestToEntity(code,request));
            }
            throw new NgoaiLe("Voucher " + request.getCode() + " đã tồn tại!");
        }
        else {
            return repository.save(voucherConvert.convertRequestToEntity(code,request));
        }
    }


    @Override
    public Voucher delete(Long id) {
        Voucher voucher = this.getOne(id);
        voucher.setDeleted(!voucher.getDeleted());
        return repository.save(voucher);
    }
}
