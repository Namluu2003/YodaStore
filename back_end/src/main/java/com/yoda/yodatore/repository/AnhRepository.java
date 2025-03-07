package com.yoda.yodatore.repository;


import com.yoda.yodatore.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnhRepository extends JpaRepository<Images,Long> {
//    List<AnhReponse> findByAndDeletedFalse(Long idSanPhamChiTiet);

//    List<Anh> find(SanPham sanPham, MauSac mauSac);

    @Transactional
    @Modifying
    @Query("DELETE FROM Images i WHERE i.sanPham.id = :sanPhamId")
    void deleteBySanPhamId(Long sanPhamId);

    // Optional: Method to find images by product ID
    List<Images> findBySanPhamId(Long sanPhamId);
}
