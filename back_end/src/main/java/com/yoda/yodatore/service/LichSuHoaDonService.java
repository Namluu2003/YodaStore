package com.yoda.yodatore.service;


import com.yoda.yodatore.entity.LichSuHoaDon;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.request.LichSuHoaDonRequest;
import com.yoda.yodatore.infrastructure.response.LichSuHoaDonReponse;

public interface LichSuHoaDonService {

    PhanTrang<LichSuHoaDonReponse> getAll(LichSuHoaDonRequest request);

    LichSuHoaDon getOne(Long id);
    LichSuHoaDon add(LichSuHoaDonRequest request);
    LichSuHoaDon update(Long id, LichSuHoaDonRequest request);
    LichSuHoaDon delete(Long id);
}
