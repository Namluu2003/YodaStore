package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.DanhMuc;
import com.yoda.yodatore.entity.KichThuoc;
import com.yoda.yodatore.entity.ThuongHieu;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.response.KichThuocResponse;
import com.yoda.yodatore.infrastructure.response.ThuongHieuResponse;

public interface ThuongHieuService {
    PhanTrang<ThuongHieuResponse> getAll(String name, Integer page, Boolean status);

    ThuongHieu getOne(Long id);

    ThuongHieu add(ThuongHieu thuongHieu);

    ThuongHieu update(Long id, ThuongHieu thuongHieu);

    ThuongHieu delete(Long id);
}
