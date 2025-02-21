package com.yoda.yodatore.infrastructure.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoda.yodatore.infrastructure.common.PageableRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

public class HoaDonRequest extends PageableRequest {


    private BigDecimal tienGiam;

    private BigDecimal tienVanChuyen;

    private Integer trangThai;

    private BigDecimal tongTien;

    private BigDecimal tienShip;

    private Integer loai;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime ngayMongMuon;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime ngayThanhToan;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime ngayNhan;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime shipDate;

    private String email;

    private String soDienThoai;

    private String diaChi;

    @NotEmpty(message = "code không được để trống!")
    private String code;

    private String customerName;

    private String ghiChu;

    private Long account;

    private Long customer;

    private Long voucher;

}
