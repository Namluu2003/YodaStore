package com.yoda.yodatore.infrastructure.request.bill;

import com.yoda.yodatore.infrastructure.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BillRequest extends PageableRequest {
    private Long id;
    private String code;
    private Long account;
    private Long voucher;
    private Long customer;
    private Integer type;
    private String customerName;
    private String phoneNumber;
    private String address;
    private BigDecimal moneyShip;
    private BigDecimal moneyReduce;
    private BigDecimal totalMoney;
    private String note;
    private Integer status;
    private Integer paymentMethod;
}
