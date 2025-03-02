package com.yoda.yodatore.repository;

import com.yoda.yodatore.entity.BillDetail;
import com.yoda.yodatore.infrastructure.request.BillDetailRequest;
import com.yoda.yodatore.infrastructure.response.BillDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBillDetailRepository extends JpaRepository<BillDetail, Long> {
    @Query("SELECT detail FROM BillDetail detail " +
            "WHERE detail.bill.id = :id ")
    BillDetail findBillDetail(@Param("id") Long id);

    List<BillDetail> findByBillId(Long id);

    BillDetail findByShoeDetailCodeAndBillId(String codeShoeDetail, Long idBill);

    Boolean existsByShoeDetailIdAndBillId(Long idShoeDetail, Long idBill);

    @Query(value = """
    SELECT ROW_NUMBER() OVER(ORDER BY s.create_at DESC) AS indexs,
        bd.id AS id,
        CONCAT(s.name, CHAR(10), sz.name, CHAR(10), c.name) AS name,
        s.name AS name,
        sd.code AS shoeCode,
        c.name AS color,
        sz.name AS size,
        bd.quantity AS quantity,
        bd.price AS price,
        GROUP_CONCAT(DISTINCT img.name) AS images
    FROM bill_detail bd
    JOIN san_pham_chi_tiet sd ON sd.id = bd.san_pham_chi_tiet_id
    JOIN san_pham s ON s.id = sd.san_pham_id
    JOIN mau_sac c ON sd.mau_sac_id = c.id
    JOIN kich_thuoc sz ON sd.kich_thuoc_id = sz.id
    LEFT JOIN (
        SELECT san_pham_id,\s
            GROUP_CONCAT(DISTINCT name ORDER BY create_at ASC) AS name
        FROM images\s
        GROUP BY san_pham_id
    ) img ON s.id = img.san_pham_id
    
    WHERE bd.hoa_don_id = :#{#req.bill}
    GROUP BY bd.id
""", nativeQuery = true)
    Page<BillDetailResponse> getAllBillDetail(@Param("req") BillDetailRequest request, Pageable pageable);


}
