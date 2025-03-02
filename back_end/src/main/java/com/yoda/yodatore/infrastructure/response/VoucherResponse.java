package com.yoda.yodatore.infrastructure.response;

import com.yoda.yodatore.entity.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Projection(types = {Voucher.class})
public interface VoucherResponse {
    @Value("#{target.indexs}")
    Integer getIndex();

    Long getId();

    String getCode();

    String getName();
    Integer getQuantity();
    Float getPercentReduce();
    BigDecimal getMinBillValue();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    Integer getStatus();
}
