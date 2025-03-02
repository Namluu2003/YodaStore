package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.KhuyenMai;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.request.KhuyenMaiRequest;
import com.yoda.yodatore.infrastructure.response.KhuyenMaiResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KhuyenMaiService {
    PhanTrang<KhuyenMaiResponse> getAll(KhuyenMaiRequest request);
    ResponseObject create(KhuyenMaiRequest request);
    ResponseObject update(Long id,KhuyenMaiRequest request);
    KhuyenMaiResponse getOne(Long id);
    List<Long> getListIdShoePromotion(Long idPromotion);
    List<Long> getListIdShoeDetailInPromotion(@Param("idPromotion") Long idPromotion);
    void deleteAll(Long idPromotion);
    void updateStatusPromotion();
    KhuyenMai updateEndDate(Long id);
}
