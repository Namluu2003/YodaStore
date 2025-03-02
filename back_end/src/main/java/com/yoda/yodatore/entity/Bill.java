package com.yoda.yodatore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yoda.yodatore.entity.base.PrimaryEnity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "hoa_don")

public class Bill extends PrimaryEnity {
    @ManyToOne
    @JsonIgnoreProperties(value = {"createAt", "updateAt", "createBy", "updateBy", "deleted"})
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties(value = {"createAt", "updateAt", "createBy", "updateBy", "deleted", "addresses", "password", "role"})
    private Account account;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties(value = {"createAt", "updateAt", "createBy", "updateBy", "deleted", "addresses", "password", "role"})
    private Account customer;
    @Column(name = "code")
    private String code;
    @Column(name = "type")
    private Integer type;
    @Nationalized
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Nationalized
    @Column(name = "address")
    private String address;
    @Column(name = "money_ship")
    private BigDecimal moneyShip;
    @Column(name = "money_reduce")
    private BigDecimal moneyReduce;
    @Column(name = "total_money")
    private BigDecimal totalMoney;
    @Column(name = "pay_date")
    private Date payDate;
    @Column(name = "ship_date")
    private Date shipDate;
    @Column(name = "desired_date")
    private Date desiredDate;
    @Column(name = "receive_date")
    private Date receiveDate;
    @Column(name = "status")
    private Integer status;
    @Column(name = "note")
    private String note;

}
