package com.yoda.yodatore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yoda.yodatore.entity.base.PrimaryEnity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet extends PrimaryEnity {

    @Column(name = "name" , length = 50)
    private String code;
    @Column(name = "gia")
    private BigDecimal gia;
    @Column(name = "so_luong")
    private Integer so_luong;
    @Column(name = "can_nang")
    private Double can_nang;



    @ManyToOne
    @JoinColumn(name = "san_pham_id")
    @JsonIgnoreProperties(value = {"creatAt","updateAt","createBy","updateBy","deleted"})
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "kich_thuoc_id")
    @JsonIgnoreProperties(value = {"creatAt","updateAt","createBy","updateBy","deleted"})
    private KichThuoc kichThuoc;

    @ManyToOne
    @JoinColumn(name = "de_id")
    @JsonIgnoreProperties(value = {"creatAt","updateAt","createBy","updateBy","deleted"})
    private De de;

    @ManyToOne
    @JoinColumn(name = "mau_sac_id")
    @JsonIgnoreProperties(value = {"creatAt","updateAt","createBy","updateBy","deleted"})
    private MauSac mauSac;


}
