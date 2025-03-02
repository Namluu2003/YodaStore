package com.yoda.yodatore.entity;


import com.yoda.yodatore.entity.base.PrimaryEnity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "khuyen_mai_chi_tiet")

public class KhuyenMaiChiTiet extends PrimaryEnity {
    @ManyToOne
    @JoinColumn(name = "san_pham_chi_tiet_id")
    private SanPhamChiTiet shoeDetail;
    @ManyToOne
    @JoinColumn(name = "khuyen_mai_id")
    private KhuyenMai promotion;
    @Column(name = "gia_khuyen_mai")
    private BigDecimal promotionPrice;
}
