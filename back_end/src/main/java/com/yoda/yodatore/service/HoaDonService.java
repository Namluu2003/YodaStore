package com.yoda.yodatore.service;


import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.HoaDonRequest;
import com.yoda.yodatore.infrastructure.response.HoaDonResponse;

public interface HoaDonService {

    PhanTrang<HoaDonResponse> getAll(HoaDonRequest request);

    HoaDon getOne(Long id);
    HoaDon add(HoaDonRequest request);
    HoaDon update(Long id, HoaDonRequest request);
    HoaDon delete(Long id);

}
