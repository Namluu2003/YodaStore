package com.yoda.yodatore.repository;

import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountVoucherRepository extends JpaRepository<AccountVoucher, Long> {

    // Kiểm tra xem tài khoản đã có voucher này chưa
    Boolean existsByAccount_IdAndVoucher_Id(Long accountId, Long voucherId);

    // Lấy danh sách voucher của một tài khoản
    List<AccountVoucher> findByAccount_Id(Long accountId);

    // Lấy thông tin của một voucher cụ thể được gán cho tài khoản
    Optional<AccountVoucher> findByAccount_IdAndVoucher_Id(Long accountId, Long voucherId);

    // Truy vấn danh sách voucher theo điều kiện lọc
    @Query("""
            SELECT av FROM AccountVoucher av
            WHERE (:#{#req.accountId} IS NULL OR av.account.id = :#{#req.accountId})
            AND (:#{#req.voucherId} IS NULL OR av.voucher.id = :#{#req.voucherId})
            AND (:#{#req.deleted} IS NULL OR av.deleted = :#{#req.deleted})
            """)
    Page<AccountVoucher> searchAccountVouchers(
            @Param("req") AccountVoucherRequest req,
            Pageable pageable
    );
}
