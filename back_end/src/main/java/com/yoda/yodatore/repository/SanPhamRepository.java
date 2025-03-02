package com.yoda.yodatore.repository;

import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.request.SanPhamRequest;
import com.yoda.yodatore.infrastructure.response.SanPhamKhuyenMaiRespone;
import com.yoda.yodatore.infrastructure.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
    Boolean existsByNameIgnoreCase(String name);

//    @Query(value = """
//            SELECT
//            s.id AS id,s.name AS name,
//            ROW_NUMBER() OVER(ORDER BY s.create_at DESC) AS indexs,
//            GROUP_CONCAT(DISTINCT (CONCAT('{ \"id\": \"', c.id, '\",\"name\": \"', c.name, '\"}'))) AS color,
//            GROUP_CONCAT(DISTINCT (CONCAT('{ \"id\": \"', sz.id, '\",\"name\": \"', sz.name, '\"}'))) AS size,
//
//            s.mo_ta AS description,
//            SUM(sd.so_luong) AS quantity,
//            ct.name AS category,
//            br.name AS brand,
//            d.name AS sole,
//            MAX(sd.gia) AS maxPrice,
//            MIN(sd.gia) AS minPrice,
//            s.deleted AS status
//            FROM san_pham s
//            LEFT JOIN san_pham_chi_tiet sd ON s.id = sd.san_pham_id
//            LEFT JOIN mau_sac c ON c.id = sd.mau_sac_id
//            LEFT JOIN kich_thuoc sz ON sz.id = sd.kich_thuoc_id
//            LEFT JOIN danh_muc ct ON ct.id = s.danh_muc_id
//            LEFT JOIN thuong_hieu br ON br.id = s.thuong_hieu_id
//            LEFT JOIN de d ON d.id = s.de_id
//            LEFT JOIN images img ON img.san_pham_id = s.id
//            WHERE (:#{#req.name} IS NULL OR s.name LIKE %:#{#req.name}%)
//            AND (:#{#req.brand} IS NULL OR s.thuong_hieu_id = :#{#req.brand})
//            AND (:#{#req.category} IS NULL OR s.danh_muc_id = :#{#req.category})
//            AND (:#{#req.sole} IS NULL OR s.de_id = :#{#req.sole})
//            AND (:#{#req.status} IS NULL OR s.deleted = :#{#req.status})
//            GROUP BY s.id
//            """, nativeQuery = true)
//    Page<SanPhamResponse> getAllShoe(@Param("req") SanPhamRequest request, Pageable pageable);

    @Query(value = """
   SELECT
       s.id AS id,
       s.name AS name,
       ROW_NUMBER() OVER(ORDER BY s.create_at DESC) AS indexs,
       -- Nhóm màu sắc thành JSON
       GROUP_CONCAT(DISTINCT CONCAT('{ \\"id\\": \\"', c.id, '\\",\\"name\\": \\"', c.name, '\\"}')) AS color,
       -- Nhóm kích thước thành JSON
       GROUP_CONCAT(DISTINCT CONCAT('{ \\"id\\": \\"', sz.id, '\\",\\"name\\": \\"', sz.name, '\\"}')) AS size,
       -- Lấy danh sách ảnh từ subquery
       (SELECT GROUP_CONCAT(DISTINCT img.name ORDER BY img.create_at ASC)\s
        FROM images img\s
        WHERE img.san_pham_id = s.id) AS images,
       
       s.mo_ta AS description,
       -- Tính tổng số lượng từ bảng chi tiết sản phẩm mà không bị nhân bản bởi JOIN với ảnh
       COALESCE(SUM(sd.so_luong), 0) AS quantity,
       ct.name AS category,
       br.name AS brand,
       d.name AS sole,
       MAX(sd.gia) AS maxPrice,
       MIN(sd.gia) AS minPrice,
       s.deleted AS status
   FROM san_pham s
   LEFT JOIN san_pham_chi_tiet sd ON s.id = sd.san_pham_id
   LEFT JOIN mau_sac c ON c.id = sd.mau_sac_id
   LEFT JOIN kich_thuoc sz ON sz.id = sd.kich_thuoc_id
   LEFT JOIN danh_muc ct ON ct.id = s.danh_muc_id
   LEFT JOIN thuong_hieu br ON br.id = s.thuong_hieu_id
   LEFT JOIN de d ON d.id = s.de_id
   WHERE (:#{#req.name} IS NULL OR s.name LIKE %:#{#req.name}%)
   AND (:#{#req.brand} IS NULL OR s.thuong_hieu_id = :#{#req.brand})
   AND (:#{#req.category} IS NULL OR s.danh_muc_id = :#{#req.category})
   AND (:#{#req.sole} IS NULL OR s.de_id = :#{#req.sole})
   AND (:#{#req.status} IS NULL OR s.deleted = :#{#req.status})
   GROUP BY s.id, s.name, s.mo_ta, ct.name, br.name, d.name, s.deleted;
   
""", nativeQuery = true)
    Page<SanPhamResponse> getAllShoe(@Param("req") SanPhamRequest request, Pageable pageable);

    @Query(value = """
            SELECT
            s.id AS id,s.name AS name,
            ROW_NUMBER() OVER(ORDER BY s.create_at DESC) AS indexs,
            ct.name AS category,
            br.name AS brand,
            pm.id AS discount
            FROM san_pham s
            LEFT JOIN san_pham_chi_tiet sd ON s.id = sd.san_pham_id
            LEFT JOIN danh_muc ct ON ct.id = s.danh_muc_id
            LEFT JOIN thuong_hieu br ON br.id = s.thuong_hieu_id
            LEFT JOIN de d ON d.id = s.de_id
            LEFT JOIN khuyen_mai_chi_tiet pmd ON pmd.khuyen_mai_chi_tiet_id = sd.id
            LEFT JOIN khuyen_mai pm ON pm.id = pmd.khuyen_mai_id
            WHERE (:promotion IS NULL OR pm.id = :promotion)
            """, nativeQuery = true)
    List<SanPhamKhuyenMaiRespone> getAllShoeInPromotion(@Param("khuyen_mai") Long promotion);
}
