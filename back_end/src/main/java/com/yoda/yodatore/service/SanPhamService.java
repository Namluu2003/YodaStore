package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.SanPhamRequest;
import com.yoda.yodatore.infrastructure.response.SanPhamKhuyenMaiRespone;
import com.yoda.yodatore.infrastructure.response.SanPhamResponse;

import java.util.List;

public interface SanPhamService {
    PhanTrang<SanPhamResponse> getAll(SanPhamRequest request);
    SanPham getOne(Long id);
    SanPham create(SanPhamRequest request);
    SanPham update(Long id,SanPhamRequest request);
    SanPham delete(Long id);

    List<SanPhamKhuyenMaiRespone> getAllShoeInPromotion(Long promotion);
}
