package com.yoda.yodatore.repository;


import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.infrastructure.request.HoaDonRequest;
import com.yoda.yodatore.infrastructure.response.HoaDonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    Boolean existsByCodeIgnoreCase(String code);

    @Query(value = """
        SELECT
            h.id AS id,
            h.code AS code,
            h.tong_tien AS tongTien,
            h.tien_giam AS tienGiam,
            h.tien_ship AS tienShip,
            h.tien_van_chuyen AS tienVanChuyen,
            h.trang_thai AS trangThai,
            h.loai AS loai,
            h.ngay_thanh_toan AS ngayThanhToan,
            h.ngay_mong_muon AS ngayMongMuon,
            h.ngay_nhan AS ngayNhan,
            h.ship_date AS shipDate,
            h.email AS email,
            h.so_dien_thoai AS soDienThoai,
            h.dia_chi AS diaChi,
            h.customer_name AS customerName,
            h.ghi_chu AS ghiChu,
            h.account_id AS accountId,
            h.customer_id AS customerId,
            h.voucher_id AS voucherId,
            ROW_NUMBER() OVER(ORDER BY h.ngay_thanh_toan DESC) AS indexs
        FROM hoa_don h
        WHERE (:#{#req.code} IS NULL OR h.code LIKE %:#{#req.code}%)
        AND (:#{#req.trangThai} IS NULL OR h.trang_thai = :#{#req.trangThai})
        AND (:#{#req.customerName} IS NULL OR h.customer_name LIKE %:#{#req.customerName}%)
        AND (:#{#req.soDienThoai} IS NULL OR h.so_dien_thoai LIKE %:#{#req.soDienThoai}%)
        """,
            nativeQuery = true)
    Page<HoaDonResponse> getAllHoaDon(@Param("req") HoaDonRequest request, Pageable pageable);

}
