package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.entity.HoaDonChiTiet;
import com.yoda.yodatore.entity.LichSuHoaDon;
import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.request.HoaDonChiTietRequest;
import com.yoda.yodatore.infrastructure.request.LichSuHoaDonRequest;
import com.yoda.yodatore.repository.HoaDonRepository;
import com.yoda.yodatore.repository.LichSuHoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LichSuHoaDonConverter {

    @Autowired
    private LichSuHoaDonRepository lichSuHoaDonRepositoryhoaDonRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    public LichSuHoaDon addconvertRequest(LichSuHoaDonRequest request){

        HoaDon hoaDon = hoaDonRepository.findById(request.getHoaDon()).get();

        LichSuHoaDon lichSuHoaDon = LichSuHoaDon.builder()
                .trangThai(request.getTrangThai())
                .ghiChu(request.getGhiChu())
                .hoaDon(hoaDon)
                .build();
        return lichSuHoaDon;
    }

    public LichSuHoaDon convertRequestToEntity(LichSuHoaDon entity, LichSuHoaDonRequest request){

        HoaDon hoaDon = hoaDonRepository.findById(request.getHoaDon()).get();

        entity.setTrangThai(request.getTrangThai());
        entity.setGhiChu(request.getGhiChu());
        entity.setHoaDon(hoaDon);

        return entity;
    }
}
