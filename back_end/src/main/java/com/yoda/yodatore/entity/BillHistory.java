package com.yoda.yodatore.entity;

import com.yoda.yodatore.entity.base.PrimaryEnity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity
@Table(name = "lich_su_hoa_don")
public class BillHistory extends PrimaryEnity {
    @ManyToOne
    @JoinColumn(name = "hoa_don_id")
    private Bill bill;
    @Nationalized
    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private Integer status;
}
