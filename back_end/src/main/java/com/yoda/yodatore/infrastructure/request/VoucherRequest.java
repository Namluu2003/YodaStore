package com.yoda.yodatore.infrastructure.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoda.yodatore.infrastructure.common.PageableRequest;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

public class VoucherRequest extends PageableRequest {

    private Long id;

    private BigDecimal giaTriToiThieu;

    private Float phanTramGiam;

    private Integer soLuong;

    private Integer trangThai;

    private Boolean loai;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime ngayBatDau;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime ngayKetThuc;

    @NotEmpty(message = "code không được để trống!")
    private String code;

    private String name;
}
