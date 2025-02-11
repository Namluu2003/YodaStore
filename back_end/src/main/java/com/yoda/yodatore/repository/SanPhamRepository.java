package com.yoda.yodatore.repository;

import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.request.SanPhamRequest;
import com.yoda.yodatore.infrastructure.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
    Boolean existsByNameIgnoreCase(String name);

    @Query("""
            SELECT s.id AS id,
            s.name AS name,
            s.thuongHieu AS thuongHieu,
            s.danhMuc AS danhMuc,
            SUM(sd.so_luong) AS quantity
            FROM SanPham s LEFT JOIN s.SanPhamChiTiet sd
            WHERE (:#{#req.name} IS NULL OR s.name LIKE %:#{#req.name}%)
            AND (:#{#req.thuongHieu} IS NULL OR s.thuongHieu.id = :#{#req.thuongHieu})
            AND (:#{#req.danhMuc} IS NULL OR s.danhMuc.id = :#{#req.danhMuc})
            GROUP BY s.id, s.name, s.thuongHieu, s.danhMuc,s.updateAt
            ORDER BY s.updateAt DESC
            """)
    Page<SanPhamResponse> getAllShoe(@Param("req") SanPhamRequest request, Pageable pageable);
}
