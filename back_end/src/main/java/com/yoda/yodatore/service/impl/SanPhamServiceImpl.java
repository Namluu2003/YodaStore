package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.Images;
import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.SanPhamConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.SanPhamRequest;
import com.yoda.yodatore.infrastructure.response.SanPhamKhuyenMaiRespone;
import com.yoda.yodatore.infrastructure.response.SanPhamResponse;
import com.yoda.yodatore.repository.AnhRepository;
import com.yoda.yodatore.repository.SanPhamChiTietRepository;
import com.yoda.yodatore.repository.SanPhamRepository;
import com.yoda.yodatore.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private SanPhamConvert sanPhamConvert;

    @Autowired
    private AnhRepository anhRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;
    public PhanTrang<SanPhamResponse> getAll(SanPhamRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PhanTrang<>(sanPhamRepository.getAllShoe(request, pageable));

    }

    public SanPham getOne(Long id){
        return sanPhamRepository.findById(id).get();
    }

    @Transactional
    public SanPham create(SanPhamRequest request) {
        if (sanPhamRepository.existsByNameIgnoreCase(request.getName())) {
            throw new NgoaiLe(request.getName() + " đã tồn tại");
        }

        SanPham sanPham = sanPhamConvert.addconvertRequest(request);
        SanPham savedSanPham = sanPhamRepository.save(sanPham);

        if (request.getListImages() != null && request.getListImages().size() > 3) {
            throw new NgoaiLe("Chỉ được thêm tối đa 3 hình ảnh!");
        }

        List<Images> savedImages = new ArrayList<>(); // Danh sách để lưu các ảnh
        if (request.getListImages() != null && !request.getListImages().isEmpty()) {
            for (String imgUrl : request.getListImages()) {
                Images image = Images.builder()
                        .sanPham(savedSanPham)
                        .name(imgUrl)
                        .build();
                Images savedImage = anhRepository.save(image);
                savedImages.add(savedImage); // Thêm ảnh đã lưu vào danh sách
            }
            savedSanPham.setImages(savedImages); // Cập nhật danh sách images trong savedSanPham
        }

        savedSanPham.getImages().size(); // Gọi để buộc tải hoặc xác nhận không null
        return savedSanPham;
    }


    public SanPham update(Long id, SanPhamRequest request) {
    SanPham name = sanPhamRepository.findById(id).get();
    if (sanPhamRepository.existsByNameIgnoreCase(request.getName())) {
        if (name.getName().equals(request.getName())) {
            return sanPhamRepository.save(sanPhamConvert.convertRequestToEntity(name, request));
        }
        throw new NgoaiLe(request.getName() + " đã tồn tại!");
    } else {
        return sanPhamRepository.save(sanPhamConvert.convertRequestToEntity(name, request));
    }
}
//    public SanPham delete(Long id) {
//        return null;
//    }
    public SanPham delete(Long id) {
        SanPham sanPham = this.getOne(id);
        sanPham.setDeleted(!sanPham.getDeleted());
        return sanPhamRepository.save(sanPham);
    }

    public SanPham changeStatus(Long id) {
        SanPham shoe = sanPhamRepository.findById(id).get();
        shoe.setDeleted(shoe.getDeleted() == false ? true : false);
        sanPhamRepository.save(shoe);
        sanPhamChiTietRepository.findByShoe(shoe).forEach(shoeDetail -> {
            shoeDetail.setDeleted(shoeDetail.getDeleted() == false ? true : false);
            sanPhamChiTietRepository.save(shoeDetail);
        });
        return shoe;
    }

    public List<SanPhamKhuyenMaiRespone> getAllShoeInPromotion(Long promotion) {
        return sanPhamRepository.getAllShoeInPromotion(promotion);
    }


//    public List<SanPhamResponse> getTopSell(Integer top) {
//        return sanPhamRepository.topSell(top);
//    }
}
