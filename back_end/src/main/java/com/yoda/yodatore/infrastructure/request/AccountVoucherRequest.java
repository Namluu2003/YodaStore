package com.yoda.yodatore.infrastructure.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yoda.yodatore.entity.Account;
import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.common.PageableRequest;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountVoucherRequest extends PageableRequest {

    private Long id;

    private Long account;

    private Long voucher;
}
