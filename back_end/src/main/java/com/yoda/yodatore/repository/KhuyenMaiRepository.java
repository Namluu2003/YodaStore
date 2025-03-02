package com.yoda.yodatore.repository;


import com.yoda.yodatore.entity.KhuyenMai;
import com.yoda.yodatore.infrastructure.request.KhuyenMaiRequest;
import com.yoda.yodatore.infrastructure.response.KhuyenMaiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai,Long> {
    @Query(value = """
            SELECT p.id AS id,
            ROW_NUMBER() OVER(ORDER BY p.create_at DESC) AS indexs,
            p.code AS code, p.name AS name,
            p.value AS value,
            p.start_date AS startDate,
            p.end_date AS endDate, p.status AS status
            FROM promotion p 
            WHERE (:#{#req.name} IS NULL OR p.name LIKE %:#{#req.name}%)
            AND (:#{#req.status} IS NULL OR p.status = :#{#req.status})
            """, nativeQuery = true)
    Page<KhuyenMaiResponse> getAllPromotion(@Param("req") KhuyenMaiRequest request, Pageable pageable);

    @Query(value = """
            SELECT p.id AS id,
            ROW_NUMBER() OVER(ORDER BY p.create_at DESC) AS indexs,
            p.code AS code, p.name AS name,
            p.value AS value,
            p.start_date AS startDate,
            p.end_date AS endDate, p.status AS status
            FROM promotion p 
            WHERE p.id = :id
            """, nativeQuery = true)
    KhuyenMaiResponse getOnePromotion(@Param("id") Long id);

    Optional<KhuyenMai> findByCode(String code);
}
