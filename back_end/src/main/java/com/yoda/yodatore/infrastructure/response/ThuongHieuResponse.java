package com.yoda.yodatore.infrastructure.response;


import com.yoda.yodatore.entity.ThuongHieu;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ThuongHieu.class})
public interface ThuongHieuResponse {
    Long getId();
    String getName();
}
