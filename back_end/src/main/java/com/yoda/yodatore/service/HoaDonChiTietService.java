package com.yoda.yodatore.service;


import com.yoda.yodatore.entity.HoaDonChiTiet;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.HoaDonChiTietRequest;
import com.yoda.yodatore.infrastructure.response.HoaDonChiTietResponse;

public interface HoaDonChiTietService {

    PhanTrang<HoaDonChiTietResponse> getAll(HoaDonChiTietRequest request);

    HoaDonChiTiet getOne(Long id);
    HoaDonChiTiet add(HoaDonChiTietRequest request);
    HoaDonChiTiet update(Long id, HoaDonChiTietRequest request);
    HoaDonChiTiet delete(Long id);

}
