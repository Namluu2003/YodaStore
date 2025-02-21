package com.yoda.yodatore.infrastructure.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.common.PageableRequest;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class HoaDonChiTietRequest extends PageableRequest {

    private BigDecimal gia;

    private Integer soLuong;

    private Boolean trangThai;

    private Long hoaDon;

    private Long sanPham;

}
