package com.yoda.yodatore.infrastructure.response;


import com.yoda.yodatore.entity.MauSac;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {MauSac.class})
public interface MauSacResponse {
    Long getId();
    String getName();
}
