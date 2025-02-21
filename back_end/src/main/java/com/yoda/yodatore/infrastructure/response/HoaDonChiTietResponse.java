package com.yoda.yodatore.infrastructure.response;



import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.entity.SanPham;

import java.math.BigDecimal;

public interface HoaDonChiTietResponse {

     BigDecimal getGia();

     Integer getSoLuong();

     Boolean getTrangThai();

     Long  getHoaDon();

     Long  getSanPham();

}
