package com.yoda.yodatore.infrastructure.request;

import com.yoda.yodatore.infrastructure.common.PageableRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountVoucherRequest extends PageableRequest {

    @NotNull(message = "Account ID không được để trống!")
    private Long accountId; // ID của tài khoản

    @NotNull(message = "Voucher ID không được để trống!")
    private Long voucherId; // ID của voucher

}
