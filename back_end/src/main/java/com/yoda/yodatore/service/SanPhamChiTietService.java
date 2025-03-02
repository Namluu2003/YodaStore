package com.yoda.yodatore.service;

import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.FindShoeDetailRequest;
import com.yoda.yodatore.infrastructure.request.SanPhamChiTietRequest;
import com.yoda.yodatore.infrastructure.response.SanPhamChiTietReponse;
import com.yoda.yodatore.entity.SanPhamChiTiet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SanPhamChiTietService {
    PhanTrang<SanPhamChiTietReponse> getAll(FindShoeDetailRequest request);
    SanPhamChiTiet getOne(Long id);
    String create(List<SanPhamChiTietRequest> list);
    SanPhamChiTiet update(Long id, SanPhamChiTietRequest request);
    SanPhamChiTiet delete(Long id);

    Map<String, BigDecimal> findMinAndMaxPrice();
    SanPhamChiTietReponse getOneShoeDetail(Long id);
}
