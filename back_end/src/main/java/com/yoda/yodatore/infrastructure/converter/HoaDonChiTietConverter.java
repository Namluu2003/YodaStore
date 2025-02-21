package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.Account;
import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.entity.HoaDonChiTiet;
import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.request.HoaDonChiTietRequest;
import com.yoda.yodatore.repository.HoaDonRepository;
import com.yoda.yodatore.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HoaDonChiTietConverter {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    public HoaDonChiTiet addconvertRequest(HoaDonChiTietRequest request){

        HoaDon hoaDon = hoaDonRepository.findById(request.getHoaDon()).get();
        SanPham sanPham = sanPhamRepository.findById(request.getSanPham()).get();

        HoaDonChiTiet hoaDonChiTiet = HoaDonChiTiet.builder()
                .gia(request.getGia())
                .soLuong(request.getSoLuong())
                .trangThai(request.getTrangThai())
                .hoaDon(hoaDon)
                .sanPham(sanPham)
                .build();
        return hoaDonChiTiet;
    }

    public HoaDonChiTiet convertRequestToEntity(HoaDonChiTiet entity, HoaDonChiTietRequest request){

        HoaDon hoaDon = hoaDonRepository.findById(request.getHoaDon()).get();
        SanPham sanPham = sanPhamRepository.findById(request.getSanPham()).get();

        entity.setGia(request.getGia());
        entity.setSoLuong(request.getSoLuong());
        entity.setTrangThai(request.getTrangThai());
        entity.setHoaDon(hoaDon);
        entity.setGia(request.getGia());
        entity.setSanPham(sanPham);
        return entity;
    }

}
