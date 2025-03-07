package com.yoda.yodatore.infrastructure.response;

import com.yoda.yodatore.entity.DanhMuc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(types = {DanhMuc.class})
public interface DanhMucResponse {
    @Value("#{target.indexs}")
    Integer getIndex();
    Long getId();
    String getName();
    Boolean getStatus();
    LocalDateTime getCreateAt();
}
