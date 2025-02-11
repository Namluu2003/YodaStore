package com.yoda.yodatore.infrastructure.response;


import com.yoda.yodatore.entity.De;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {De.class})
public interface DeResponse {
    Long getId();
    String getName();
}
