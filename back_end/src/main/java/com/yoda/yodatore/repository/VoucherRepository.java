package com.yoda.yodatore.repository;

import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import com.yoda.yodatore.infrastructure.response.VoucherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    Boolean existsByCodeIgnoreCase(String code);

    @Query(value = """
    SELECT
        v.id AS id,
        v.code AS code,
        v.name AS name,
        v.trang_thai AS trangThai,
        v.gia_tri_toi_thieu AS giaTriToiThieu,
        v.phan_tram_giam AS phanTramGiam,
        v.so_luong AS soLuong,
        v.loai AS loai,
        v.ngay_bat_dau AS ngayBatDau,
        v.ngay_ket_thuc AS ngayKetThuc,
        ROW_NUMBER() OVER(ORDER BY v.create_at DESC) AS indexs
    FROM voucher v
    WHERE (:#{#req.code} IS NULL OR v.code LIKE %:#{#req.code}%)
    AND (:#{#req.name} IS NULL OR v.name LIKE %:#{#req.name}%)
    AND (:#{#req.trangThai} IS NULL OR v.trang_thai = :#{#req.trangThai})
    GROUP BY v.id, v.code, v.name, v.trang_thai, v.gia_tri_toi_thieu, v.phan_tram_giam, 
             v.so_luong, v.loai, v.ngay_bat_dau, v.ngay_ket_thuc
    """, nativeQuery = true)
    Page<VoucherResponse> getAllVoucher(@Param("req") VoucherRequest request, Pageable pageable);

}
