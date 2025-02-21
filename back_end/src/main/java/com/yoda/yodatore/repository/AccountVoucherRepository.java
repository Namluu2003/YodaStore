package com.yoda.yodatore.repository;


import com.yoda.yodatore.entity.AccountVoucher;
import com.yoda.yodatore.infrastructure.request.AccountVoucherRequest;
import com.yoda.yodatore.infrastructure.response.AccountVoucherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountVoucherRepository extends JpaRepository<AccountVoucher, Long> {

    @Query(value = """
    SELECT
        av.id AS id,
        av.account_id AS account,
        av.voucher_id AS voucher,
        av.create_at AS createAt,
        av.update_at AS updateAt,
        av.create_by AS createBy,
        av.update_by AS updateBy,
        ROW_NUMBER() OVER(ORDER BY av.create_at DESC) AS indexs
    FROM account_voucher av
    WHERE (:#{#req.account} IS NULL OR av.account_id = :#{#req.account})
    AND (:#{#req.voucher} IS NULL OR av.voucher_id = :#{#req.voucher})
    GROUP BY av.id, av.account_id, av.voucher_id, av.create_at, av.update_at, av.create_by, av.update_by
    """, nativeQuery = true)
    Page<AccountVoucherResponse> getAllAccountVoucher(@Param("req") AccountVoucherRequest request, Pageable pageable);

}
