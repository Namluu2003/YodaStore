package com.yoda.yodatore.infrastructure.response;

import com.yoda.yodatore.entity.DanhMuc;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {DanhMuc.class})
public interface DanhMucResponse {
    Long getId();
    String getName();
}
