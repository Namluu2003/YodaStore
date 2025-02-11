package com.yoda.yodatore.repository;

import com.yoda.yodatore.entity.De;
import com.yoda.yodatore.infrastructure.response.DeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeRepository extends JpaRepository<De, Long> {
    Boolean existsByNameIgnoreCaseAndNameNot(String name, String exceptName);

    @Query("""
            SELECT b.id as id,b.name as name FROM De b
            WHERE (:name IS NULL OR b.name LIKE %:name%)
            AND (:status IS NULL OR b.deleted = :status)
            ORDER BY b.createAt
            """)
    Page<DeResponse> getAll(@Param("name") String name, @Param("status") Boolean status, Pageable pageable);
}
