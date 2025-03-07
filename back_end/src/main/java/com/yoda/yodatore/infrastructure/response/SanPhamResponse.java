package com.yoda.yodatore.infrastructure.response;


import com.yoda.yodatore.entity.SanPham;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

@Projection(types = {SanPham.class})
public interface SanPhamResponse {
    @Value("#{target.indexs}")
    Integer getIndex();

    Long getId();

    String getName();

    String getSize();

    String getColor();

    String getBrand();

    String getCategory();
    String getSole();

    Integer getQuantity();

    Boolean getStatus();
    BigDecimal getMaxPrice();
    BigDecimal getMinPrice();
    String getDescription();
    String getImages();
//// Sửa thành List<String> để trả về danh sách URL ảnh
//@Value("#{target.images != null ? target.images.![name] : null}")
//List<String> getImages();

}
