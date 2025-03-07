package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.KichThuoc;
import com.yoda.yodatore.entity.MauSac;
import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.common.GenCode;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.converter.SanPhamChiTietConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.FindShoeDetailRequest;
import com.yoda.yodatore.infrastructure.request.SanPhamChiTietRequest;
import com.yoda.yodatore.infrastructure.request.SanPhamChiTietUpdateRequest;
import com.yoda.yodatore.infrastructure.response.SanPhamChiTietReponse;
import com.yoda.yodatore.repository.*;
import com.yoda.yodatore.entity.SanPhamChiTiet;


import com.yoda.yodatore.service.SanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private MauSacRepository mauSacRepository;
    @Autowired
    private KichThuocRepository kichThuocRepository;
    @Autowired
    private AnhRepository anhRepository;
    @Autowired
    private SanPhamChiTietConvert sanPhamChiTietConvert;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    public PhanTrang<SanPhamChiTietReponse> getAll(FindShoeDetailRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        FindShoeDetailRequest customRequest = FindShoeDetailRequest.builder()
                .colors(request.getColor() != null ? Arrays.asList(request.getColor().split(",")) : null)
                .shoes(request.getShoe() != null ? Arrays.asList(request.getShoe().split(",")) : null)
                .sizes(request.getSize() != null ? Arrays.asList(request.getSize().split(",")) : null)
                .soles(request.getSole() != null ? Arrays.asList(request.getSole().split(",")) : null)
                .size(request.getSize())
                .color(request.getColor())
                .shoe(request.getShoe())
                .sole(request.getSole())
                .name(request.getName())
                .build();
        return new PhanTrang<>(sanPhamChiTietRepository.getAll(customRequest, pageable));
    }


    public SanPhamChiTiet getOne(Long id) {
        return sanPhamChiTietRepository.findById(id).orElse(null);
    }


    @Transactional
    public String create(List<SanPhamChiTietRequest> list) {
        for (SanPhamChiTietRequest request: list) {
            SanPhamChiTiet convert = sanPhamChiTietConvert.convertRequestToEntity(request);
            SanPhamChiTiet check = sanPhamChiTietRepository.findByShoeIdAndColorIdAndSizeId(request.getShoe(), request.getColor(), request.getSize());
            if (check!= null) {
                check.setQuantity(check.getQuantity() + request.getQuantity());
                sanPhamChiTietRepository.save(check);
            }else {
                SanPhamChiTiet sanPhamChiTietsave = sanPhamChiTietRepository.save(convert);
                SanPham sanPham = sanPhamChiTietsave.getShoe();
                sanPham.setUpdateAt(LocalDateTime.now());
                sanPhamRepository.save(sanPham);

            }
        }
        return "Thêm thành công!";
    }



    public SanPhamChiTiet update(Long id, SanPhamChiTietUpdateRequest request) {
        SanPhamChiTiet old = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new NgoaiLe("Không tìm thấy sản phẩm!"));

        String newCode = GenCode.genCodeByName(old.getShoe().getName() + request.getColor() + request.getSize());

        // Kiểm tra nếu mã mới khác mã cũ và đã tồn tại
        if (!newCode.equals(old.getCode()) &&
                sanPhamChiTietRepository.existsByCode(newCode)) {
            throw new NgoaiLe("Phiên bản này đã tồn tại!");
        }

        // Cập nhật thông tin
        old.setPrice(request.getPrice());
        old.setWeight(request.getWeight());
        old.setQuantity(request.getQuantity());
        old.setSize(kichThuocRepository.findByName(request.getSize()));
        old.setColor(mauSacRepository.findByName(request.getColor()));
        old.setCode(newCode);

        return sanPhamChiTietRepository.save(old);
    }




    public ResponseObject updateFast(List<SanPhamChiTietRequest> list) {
        for (SanPhamChiTietRequest request : list) {
            SanPhamChiTiet convert = sanPhamChiTietConvert.convertRequestToEntityFast(sanPhamChiTietRepository.findById(request.getId()).get(), request);
            sanPhamChiTietRepository.save(convert);
        }
        return new ResponseObject(list);
    }




    public SanPhamChiTietReponse getOneShoeDetail(Long id) {
        return sanPhamChiTietRepository.getOneShoeDetail(id);
    }

    public Map<String, BigDecimal> findMinAndMaxPrice() {
        return sanPhamChiTietRepository.findMinAndMaxPrice();
    }

    public SanPhamChiTiet delete(Long id) {
        return null;
    }
}
