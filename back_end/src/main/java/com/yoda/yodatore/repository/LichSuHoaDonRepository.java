package com.yoda.yodatore.repository;


import com.yoda.yodatore.entity.LichSuHoaDon;
import com.yoda.yodatore.infrastructure.request.LichSuHoaDonRequest;
import com.yoda.yodatore.infrastructure.response.LichSuHoaDonReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, Long> {

    @Query(value = """
        SELECT 
            lshd.id AS id,
            lshd.trang_thai AS trangThai,
            lshd.ghi_chu AS ghiChu,
            lshd.hoa_don_id AS hoaDon,
            lshd.create_at AS createAt,
            lshd.update_at AS updateAt,
            lshd.create_by AS createBy,
            lshd.update_by AS updateBy,
            ROW_NUMBER() OVER(ORDER BY lshd.create_at DESC) AS indexs
        FROM lich_su_hoa_don lshd
        WHERE (:#{#req.hoaDon} IS NULL OR lshd.hoa_don_id = :#{#req.hoaDon})
        """,
            nativeQuery = true)
    Page<LichSuHoaDonReponse> getAllLichSuHoaDon(@Param("req") LichSuHoaDonRequest request, Pageable pageable);

}
