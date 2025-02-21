package com.yoda.yodatore.infrastructure.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.infrastructure.common.PageableRequest;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Setter
@Getter

public class LichSuHoaDonRequest extends PageableRequest {

    private Integer trangThai;

    private String ghiChu;

    private Long hoaDon;
}
