package com.yoda.yodatore.infrastructure.response;


import com.yoda.yodatore.entity.SanPham;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SanPham.class})
public interface SanPhamResponse {
    Long getId();
    String getName();
    ThuongHieuResponse getThuongHieu();
    DanhMucResponse getDanhMuc();

    Integer getSoLuong();

}
