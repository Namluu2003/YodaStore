package com.yoda.yodatore.repository;

import com.yoda.yodatore.entity.KhuyenMaiChiTiet;
import com.yoda.yodatore.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhuyenMaiChiTietRepository extends JpaRepository<KhuyenMaiChiTiet,Long> {
    Boolean existsByShoeDetailId(Long id);
    @Query(value = """
            SELECT GROUP_CONCAT(DISTINCT s.id) FROM promotion_detail pmd
            JOIN shoe_detail sd ON sd.id = pmd.shoe_detail_id
            JOIN shoe s ON s.id = sd.shoe_id
            JOIN promotion pm ON pm.id = pmd.promotion_id
            WHERE :idPromotion IS NULL OR pm.id = :idPromotion
            """, nativeQuery = true)
    List<String> getListIdShoePromotion(@Param("idPromotion") Long idPromotion);

    KhuyenMaiChiTiet findByShoeDetailId(Long id);

    @Query(value = """
            SELECT pmd.shoe_detail_id FROM promotion_detail pmd
            WHERE :idPromotion IS NULL OR pmd.promotion_id = :idPromotion
            """, nativeQuery = true)
    List<String> getListIdShoeDetailInPromotion(@Param("idPromotion") Long idPromotion);

    KhuyenMaiChiTiet findByShoeDetailCode(String code);
    @Query("SELECT pd.id FROM KhuyenMaiChiTiet pd WHERE pd.promotion.id = :promotionId")
    List<Long> findIdsByPromotionId(@Param("promotionId") Long promotionId);
    Boolean existsByShoeDetail(SanPhamChiTiet shoeDetail);
}
