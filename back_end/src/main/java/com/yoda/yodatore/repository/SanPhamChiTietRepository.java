package com.yoda.yodatore.repository;

import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.entity.SanPhamChiTiet;

import com.yoda.yodatore.infrastructure.request.FindShoeDetailRequest;
import com.yoda.yodatore.infrastructure.response.SanPhamChiTietReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet,Long> {
//    SanPhamChiTiet findByCode(String code);
//
//    @Query(value = """
//            SELECT sd.id AS id,
//            ROW_NUMBER() OVER(ORDER BY s.create_at DESC) AS indexs,
//            CONCAT(s.name, ' [', c.name, ' - ', sz.name, ']') AS name,
//            sd.code AS code,
//
//            c.name AS color,
//            sz.name AS size,
//            sd.so_luong AS quantity,
//            sd.can_nang AS weight,
//            sd.gia AS price,
//            GROUP_CONCAT(DISTINCT img.name) AS images,
//            sd.deleted AS status
//            FROM san_pham_chi_tiet sd
//            JOIN san_pham s ON sd.san_pham_id = s.id
//            JOIN mau_sac c ON sd.mau_sac_id = c.id
//            JOIN kich_thuoc sz ON sd.kich_thuoc_id = sz.id
//
//            JOIN images img ON img.san_pham_chi_tiet_id = sd.id
//            WHERE (:#{#req.shoe} IS NULL OR sd.san_pham_id = :#{#req.shoe})
//            AND (:#{#req.color} IS NULL OR sd.mau_sac_id = :#{#req.color})
//            AND (:#{#req.size} IS NULL OR sd.kich_thuoc_id = :#{#req.size})
//
//            AND (:#{#req.name} IS NULL OR CONCAT(s.name, ' ', c.name, ' ', sz.name, ' ') LIKE %:#{#req.name}%)
//            GROUP BY sd.id
//            """, nativeQuery = true)
//    Page<SanPhamChiTietReponse> getAll(@Param("req") SanPhamChiTietRequest request, Pageable pageable);
//
//    SanPhamChiTiet findByShoeIdAndColorIdAndSizeId(Long idShoe, Long idColor, Long idSize);
//
//    SanPhamChiTiet findByShoeIdAndColorNameAndSizeName(Long idShoe, String colorName, String sizeNmae);

    //    Boolean existsByNameIgnoreCase(String name);
    Boolean existsByCodeAndCodeNot(String code, String exceptCode);
    Boolean existsByCode(String code);

    SanPhamChiTiet findByCode(String code);
    List<SanPhamChiTiet> findByShoe(SanPham shoe);

    @Query(value = """
    SELECT
        sd.id AS id,
        ROW_NUMBER() OVER(ORDER BY s.update_at DESC) AS indexs,
        s.name AS name,
        sd.code AS code,
        c.name AS color,
        sz.name AS size,
        sd.so_luong AS quantity,
        sd.can_nang AS weight,
        sd.gia AS price,
        CASE 
            WHEN CURRENT_TIMESTAMP BETWEEN pm.start_date AND pm.end_date 
                THEN pmd.gia_khuyen_mai
            ELSE NULL
        END AS discountValue,
        CASE 
            WHEN CURRENT_TIMESTAMP BETWEEN pm.start_date AND pm.end_date 
                THEN MAX(pm.gia_tri)
            ELSE NULL
        END AS discountPercent,
        GROUP_CONCAT(DISTINCT img.name ORDER BY img.create_at ASC) AS images, -- Đảm bảo ảnh đầu tiên là mặc định
        sd.deleted AS status
    FROM san_pham_chi_tiet sd
    LEFT JOIN san_pham s ON sd.san_pham_id = s.id
    LEFT JOIN mau_sac c ON sd.mau_sac_id = c.id
    LEFT JOIN kich_thuoc sz ON sd.kich_thuoc_id = sz.id
    LEFT JOIN images img ON img.san_pham_id = s.id
    LEFT JOIN khuyen_mai_chi_tiet pmd ON pmd.san_pham_chi_tiet_id = sd.id
    LEFT JOIN khuyen_mai pm ON pm.id = pmd.khuyen_mai_id
    WHERE
        (:#{#req.shoe} IS NULL OR sd.san_pham_id IN (:#{#req.shoes}))
        AND (:#{#req.color} IS NULL OR :#{#req.color} = '' OR sd.mau_sac_id IN (:#{#req.colors}))
        AND (:#{#req.size} IS NULL OR :#{#req.size} = '' OR sd.kich_thuoc_id IN (:#{#req.sizes}))
        AND (:#{#req.name} IS NULL OR :#{#req.name} = '' OR s.name LIKE %:#{#req.name}%)
    GROUP BY
        sd.id, s.update_at, s.name, c.name, sz.name, sd.code, sd.so_luong, sd.can_nang, sd.gia, sd.deleted, pm.start_date, pm.end_date, pmd.gia_khuyen_mai;
""", nativeQuery = true)
    Page<SanPhamChiTietReponse> getAll(@Param("req") FindShoeDetailRequest request, Pageable pageable);




    @Query(value = """
            SELECT
                sd.id AS id,
                ROW_NUMBER() OVER(ORDER BY s.update_at DESC) AS indexs,
                CONCAT(s.name, ' [', c.name, ' - ', sz.name, ']') AS name,
                sd.code AS code,
                c.name AS color,
                sz.name AS size,
                sd.so_luong AS quantity,
                sd.can_nang AS weight,
                sd.gia AS price,
                CASE 
                    WHEN CURRENT_TIMESTAMP BETWEEN pm.start_date AND pm.end_date 
                        THEN pmd.gia_khuyen_mai
                    ELSE NULL
                END AS discountValue,
                CASE 
                    WHEN CURRENT_TIMESTAMP BETWEEN pm.start_date AND pm.end_date 
                        THEN MAX(pm.value)
                    ELSE NULL
                END AS discountPercent,
                GROUP_CONCAT(DISTINCT img.name) AS images,
                sd.deleted AS status
            FROM
                san_pham_chi_tiet sd
                LEFT JOIN san_pham s ON sd.san_pham_id = s.id
                LEFT JOIN mau_sac c ON sd.mau_sac_id = c.id
                LEFT JOIN kich_thuoc sz ON sd.kich_thuoc_id = sz.id
                
                LEFT JOIN images img ON img.san_pham_chi_tiet_id = sd.id
                LEFT JOIN khuyen_mai_chi_tiet pmd ON pmd.san_pham_chi_tiet_id = sd.id
                LEFT JOIN khuyen_mai pm ON pm.id = pmd.khuyen_mai_id
            WHERE ? = sd.id
            GROUP BY
                sd.id, s.update_at, s.name, c.name, sz.name, sd.code, sd.so_luong, sd.can_nang, sd.gia, sd.deleted,pm.start_date, pm.end_date, pmd.gia_khuyen_mai;
    """, nativeQuery = true)
    SanPhamChiTietReponse getOneShoeDetail(@Param("id") Long id);


    SanPhamChiTiet findByShoeIdAndColorIdAndSizeId(Long idShoe, Long idColor, Long idSize);

    SanPhamChiTiet findByShoeIdAndColorNameAndSizeName(Long idShoe, String colorName, String sizeName);

    @Query("SELECT MIN(sd.price) AS minPrice, MAX(sd.price) AS maxPrice FROM SanPhamChiTiet sd")
    Map<String, BigDecimal> findMinAndMaxPrice();
}