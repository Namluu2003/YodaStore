package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.Images;
import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.SanPhamChiTietConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.FindShoeDetailRequest;
import com.yoda.yodatore.infrastructure.request.SanPhamChiTietRequest;
import com.yoda.yodatore.infrastructure.response.SanPhamChiTietReponse;
import com.yoda.yodatore.repository.AnhRepository;
import com.yoda.yodatore.repository.SanPhamChiTietRepository;
import com.yoda.yodatore.repository.SanPhamRepository;
import com.yoda.yodatore.entity.SanPhamChiTiet;


import com.yoda.yodatore.service.SanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {
    @Autowired
    private SanPhamRepository sanPhamRepository;
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
                if(request.getListImages().size()>3)
                    throw new NgoaiLe("Chỉ được thêm tối đa 3 hình ảnh!");
                if (sanPhamChiTietsave != null) {
                    for (String x : request.getListImages()) {
                        anhRepository.save(Images.builder().sanPhamChiTiet(sanPhamChiTietsave).name(x).build());
                    }
                }
            }
        }
        return "Thêm thành công!";
    }


    @Transactional
    public SanPhamChiTiet update(Long id, SanPhamChiTietRequest request) {
        SanPhamChiTiet sanPhamChiTietsave = new SanPhamChiTiet();
        SanPhamChiTiet old = sanPhamChiTietRepository.findById(id).get();
        if (sanPhamChiTietRepository.findByShoeIdAndColorIdAndSizeId(request.getShoe(), request.getColor(), request.getSize()) != null) {
            if (old.getSize().getId() == request.getSize() && old.getShoe().getId() == request.getShoe() && old.getColor().getId() == request.getColor() ) {
                sanPhamChiTietsave = sanPhamChiTietRepository.save(sanPhamChiTietConvert.convertRequestToEntity(old, request));
                if(sanPhamChiTietsave!=null){
                    for (String x: request.getListImages()) {
                        anhRepository.save(Images.builder().sanPhamChiTiet(sanPhamChiTietsave).name(x).build());
                    }
                }
                return sanPhamChiTietsave;
            }
            throw new NgoaiLe("Phiên bản này đã tồn tại!");
        }
        return sanPhamChiTietRepository.save(sanPhamChiTietConvert.convertRequestToEntity(old, request));
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
