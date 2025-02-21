package com.yoda.yodatore.service.impl;


import com.yoda.yodatore.entity.LichSuHoaDon;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.LichSuHoaDonConverter;
import com.yoda.yodatore.infrastructure.request.LichSuHoaDonRequest;
import com.yoda.yodatore.infrastructure.response.LichSuHoaDonReponse;
import com.yoda.yodatore.repository.LichSuHoaDonRepository;
import com.yoda.yodatore.service.LichSuHoaDonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LichSuHoaDonImpl implements LichSuHoaDonService {

    @Autowired
    private LichSuHoaDonRepository repository;

    @Autowired
    private LichSuHoaDonConverter converter;

    @Override
    public PhanTrang<LichSuHoaDonReponse> getAll(LichSuHoaDonRequest request) {

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PhanTrang<>(repository.getAllLichSuHoaDon(request, pageable));
    }

    @Override
    public LichSuHoaDon getOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public LichSuHoaDon add(LichSuHoaDonRequest request) {

        LichSuHoaDon lichSuHoaDon = converter.addconvertRequest(request);
        return repository.save(lichSuHoaDon);
    }

    @Override
    public LichSuHoaDon update(Long id, LichSuHoaDonRequest request) {

        LichSuHoaDon entit = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("X Không tìm thấy Lịch sử Hóa đơn với ID: " + id));
        return repository.save(converter.convertRequestToEntity(entit, request));
    }

    @Override
    public LichSuHoaDon delete(Long id) {
        LichSuHoaDon lichSuHoaDon = repository.findById(id).get();
        lichSuHoaDon.setDeleted(!lichSuHoaDon.getDeleted());
        return repository.save(lichSuHoaDon);
    }
}
