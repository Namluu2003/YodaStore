package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.DanhMuc;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.response.DanhMucResponse;
import com.yoda.yodatore.infrastructure.response.DeResponse;

public interface DanhMucService {
    PhanTrang<DanhMucResponse> getAll(String name, Integer page, Boolean status);

    DanhMuc getOne(Long id);

    DanhMuc add(DanhMuc danhMuc);

    DanhMuc update(Long id, DanhMuc danhMuc);

    DanhMuc delete(Long id);
}
