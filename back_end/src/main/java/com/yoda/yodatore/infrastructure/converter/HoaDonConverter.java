package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.Account;
import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.common.GenCode;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.request.HoaDonRequest;
import com.yoda.yodatore.repository.AccountRepository;
import com.yoda.yodatore.repository.HoaDonRepository;
import com.yoda.yodatore.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HoaDonConverter {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    public HoaDon addconvertRequest(HoaDonRequest request){

        Account account = accountRepository.findById(request.getAccount()).get();
        Voucher voucher = voucherRepository.findById(request.getVoucher()).get();

        HoaDon hoaDon = HoaDon.builder()
                .tienGiam(request.getTienGiam())
                .tienVanChuyen(request.getTienVanChuyen())
                .trangThai(request.getTrangThai())
                .tongTien(request.getTongTien())
                .tienShip(request.getTienShip())
                .loai(request.getLoai())
                .ngayMongMuon(request.getNgayMongMuon())
                .ngayThanhToan(request.getNgayThanhToan())
                .ngayNhan(request.getNgayNhan())
                .shipDate(request.getShipDate())
                .email(request.getEmail())
                .soDienThoai(request.getSoDienThoai())
                .diaChi(request.getDiaChi())
                .code(GenCode.genCodeByName(account.getTen() + account.getEmail()))
                .customerName(request.getCustomerName())
                .ghiChu(request.getGhiChu())
                .account(account)
                .customer(account)
                .voucher(voucher)
                .build();
        return hoaDon;
    }

    public HoaDon convertRequestToEntity(HoaDon entity, HoaDonRequest request){

        Account account = accountRepository.findById(request.getAccount()).get();
        Voucher voucher = voucherRepository.findById(request.getVoucher()).get();

        entity.setTienGiam(request.getTienGiam());
        entity.setTienVanChuyen(request.getTienVanChuyen());
        entity.setTrangThai(request.getTrangThai());
        entity.setTongTien(request.getTongTien());
        entity.setTienShip(request.getTienShip());
        entity.setLoai(request.getLoai());
        entity.setNgayMongMuon(request.getNgayMongMuon());
        entity.setNgayThanhToan(request.getNgayThanhToan());
        entity.setNgayNhan(request.getNgayNhan());
        entity.setShipDate(request.getShipDate());
        entity.setEmail(request.getEmail());
        entity.setSoDienThoai(request.getSoDienThoai());
        entity.setDiaChi(request.getDiaChi());
        entity.setCode(GenCode.genCodeByName(account.getTen() + account.getEmail()));
        entity.setCustomerName(request.getCustomerName());
        entity.setGhiChu(request.getGhiChu());
        entity.setAccount(account);
        entity.setCustomer(account);
        entity.setVoucher(voucher);
        return entity;
    }
}
