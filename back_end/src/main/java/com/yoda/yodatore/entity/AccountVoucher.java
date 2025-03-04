package com.yoda.yodatore.entity;

import com.yoda.yodatore.entity.base.PrimaryEnity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "account_voucher")
public class AccountVoucher extends PrimaryEnity {

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account; // Liên kết đến bảng `account`

    @ManyToOne
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private Voucher voucher; // Liên kết đến bảng `voucher`

}

