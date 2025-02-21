package com.yoda.yodatore.service.impl;


import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.entity.HoaDonChiTiet;
import com.yoda.yodatore.infrastructure.common.PageableRequest;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.HoaDonChiTietConverter;
import com.yoda.yodatore.infrastructure.request.HoaDonChiTietRequest;
import com.yoda.yodatore.infrastructure.response.HoaDonChiTietResponse;
import com.yoda.yodatore.repository.HoaDonChiTietRepository;
import com.yoda.yodatore.service.HoaDonChiTietService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HoaDonChiTietImpl implements HoaDonChiTietService {

    @Autowired
    private HoaDonChiTietRepository repository;

    @Autowired
    private HoaDonChiTietConverter converter;

    @Override
    public PhanTrang<HoaDonChiTietResponse> getAll(HoaDonChiTietRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PhanTrang<>(repository.getAllHoaDonChiTiet(request, pageable));
    }

    @Override
    public HoaDonChiTiet getOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public HoaDonChiTiet add(HoaDonChiTietRequest request) {

        HoaDonChiTiet hoaDonChiTiet = converter.addconvertRequest(request);
        return repository.save(hoaDonChiTiet);
    }

    @Override
    public HoaDonChiTiet update(Long id, HoaDonChiTietRequest request) {

        HoaDonChiTiet entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("X Không tìm thấy Hóa đơn chi tiết với ID: " + id));
        return repository.save(converter.convertRequestToEntity(entity, request));
    }

    @Override
    public HoaDonChiTiet delete(Long id) {
        HoaDonChiTiet hoaDonChiTiet = repository.findById(id).get();
        hoaDonChiTiet.setDeleted(!hoaDonChiTiet.getDeleted());
        return repository.save(hoaDonChiTiet);
    }
}
