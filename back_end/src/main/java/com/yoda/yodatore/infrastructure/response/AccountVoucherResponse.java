package com.yoda.yodatore.infrastructure.response;

import com.yoda.yodatore.entity.AccountVoucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(types = {AccountVoucher.class})
public interface AccountVoucherResponse {

    @Value("#{target.indexs}")
    Integer getIndex(); // Chỉ số hàng (nếu có sử dụng ROW_NUMBER)

    Long getId();

    Long getAccountId(); // ID của tài khoản

    Long getVoucherId(); // ID của voucher

    String getVoucherCode(); // Mã voucher

    String getVoucherName(); // Tên voucher

    LocalDateTime getCreateAt(); // Thời gian tạo

    LocalDateTime getUpdateAt();

    String getCreateBy();

    String getUpdateBy();
}