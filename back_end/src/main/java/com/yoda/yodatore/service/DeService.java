package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.DanhMuc;
import com.yoda.yodatore.entity.De;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.response.DeResponse;

public interface DeService {
    PhanTrang<DeResponse> getAll(String name, Integer page, Boolean status);

    De getOne(Long id);

    De add(De de);

    De update(Long id, De de);

    De delete(Long id);
}
