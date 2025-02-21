package com.yoda.yodatore.repository;


import com.yoda.yodatore.entity.HoaDonChiTiet;
import com.yoda.yodatore.infrastructure.request.HoaDonChiTietRequest;
import com.yoda.yodatore.infrastructure.response.HoaDonChiTietResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Long> {


    @Query(value = """
    SELECT 
        hct.id AS id, 
        hct.gia AS gia, 
        hct.so_luong AS soLuong, 
        hct.trang_thai AS trangThai, 
        hct.hoa_don_id AS hoaDon, 
        hct.san_pham_id AS sanPham
    FROM hoa_don_chi_tiet hct
    WHERE (:#{#req.hoaDon} IS NULL OR hct.hoa_don_id = :#{#req.hoaDon})
    AND (:#{#req.sanPham} IS NULL OR hct.san_pham_id = :#{#req.sanPham})
    """,
            nativeQuery = true)
    Page<HoaDonChiTietResponse> getAllHoaDonChiTiet(@Param("req") HoaDonChiTietRequest request, Pageable pageable);

}
