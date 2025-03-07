package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.Images;
import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.SanPhamConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.SanPhamRequest;

import com.yoda.yodatore.infrastructure.response.SanPhamResponse;
import com.yoda.yodatore.repository.*;
import com.yoda.yodatore.service.SanPhamService;
import com.yoda.yodatore.util.CloudinaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private DanhMucRepository danhMucRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private DeRepository deRepository;
    @Autowired
    private CloudinaryUtils cloudinaryUtils;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;
    public PhanTrang<SanPhamResponse> getAll(SanPhamRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PhanTrang<>(sanPhamRepository.getAllShoe(request, pageable));

    }

    public SanPham getOne(Long id){
        return sanPhamRepository.findById(id).get();
    }

//    @Transactional
//    public SanPham create(SanPhamRequest request) {
//        if (sanPhamRepository.existsByNameIgnoreCase(request.getName())) {
//            throw new NgoaiLe(request.getName() + " đã tồn tại");
//        }
//
//        SanPham sanPham = sanPhamConvert.addconvertRequest(request);
//        SanPham savedSanPham = sanPhamRepository.save(sanPham);
//
//        if (request.getListImages() != null && request.getListImages().size() > 3) {
//            throw new NgoaiLe("Chỉ được thêm tối đa 3 hình ảnh!");
//        }
//
//        List<Images> savedImages = new ArrayList<>(); // Danh sách để lưu các ảnh
//        if (request.getListImages() != null && !request.getListImages().isEmpty()) {
//            for (String imgUrl : request.getListImages()) {
//                Images image = Images.builder()
//                        .sanPham(savedSanPham)
//                        .name(imgUrl)
//                        .build();
//                Images savedImage = anhRepository.save(image);
//                savedImages.add(savedImage); // Thêm ảnh đã lưu vào danh sách
//            }
//            savedSanPham.setImages(savedImages); // Cập nhật danh sách images trong savedSanPham
//        }
//
//        savedSanPham.getImages().size(); // Gọi để buộc tải hoặc xác nhận không null
//        return savedSanPham;
//    }


    //    public SanPham create(SanPhamRequest request) {
//        if (sanPhamRepository.existsByNameIgnoreCase(request.getName())) {
//            throw new NgoaiLe(request.getName() + " đã tồn tại");
//        }
//
//        SanPham sanPham = sanPhamConvert.addconvertRequest(request);
//
//        // Save related entities if they don't exist
//        if (sanPham.getCategory() != null && sanPham.getCategory().getId() == null) {
//            danhMucRepository.save(sanPham.getCategory());
//        }
//        if (sanPham.getBrand() != null && sanPham.getBrand().getId() == null) {
//            thuongHieuRepository.save(sanPham.getBrand());
//        }
//        if (sanPham.getSole() != null && sanPham.getSole().getId() == null) {
//            deRepository.save(sanPham.getSole());
//        }
//
//        // Save product first
//        sanPham = sanPhamRepository.save(sanPham);
//
//        // Handle image uploads and URLs
//        List<String> allImageUrls = new ArrayList<>();
//
//        // Xử lý ảnh upload
//        if (request.getImages() != null && !request.getImages().isEmpty()) {
//            if (request.getImages().size() + (request.getImageUrls() != null ? request.getImageUrls().size() : 0) > 3) {
//                throw new NgoaiLe("Chỉ được thêm tối đa 3 hình ảnh!");
//            }
//            List<String> uploadedUrls = cloudinaryUtils.uploadMultipleImages(
//                    request.getImages(),
//                    "products/" + sanPham.getId()
//            );
//            allImageUrls.addAll(uploadedUrls);
//        }
//
//        // Xử lý URL từ Cloudinary
//        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
//            if (request.getImageUrls().size() + (request.getImages() != null ? request.getImages().size() : 0) > 3) {
//                throw new NgoaiLe("Chỉ được thêm tối đa 3 hình ảnh!");
//            }
//            allImageUrls.addAll(request.getImageUrls());
//        }
//
//        // Save all image URLs to database
//        for (String imageUrl : allImageUrls) {
//            anhRepository.save(
//                    Images.builder()
//                            .sanPham(sanPham)
//                            .name(imageUrl)
//                            .build()
//            );
//        }
//
//        return sanPhamRepository.save(sanPham);
//    }
    public SanPham create(SanPhamRequest request) {
        if (sanPhamRepository.existsByNameIgnoreCase(request.getName())) {
            throw new NgoaiLe(request.getName() + " đã tồn tại");
        }

        SanPham sanPham = sanPhamConvert.addconvertRequest(request);

        if (sanPham.getCategory() != null && sanPham.getCategory().getId() == null) {
            danhMucRepository.save(sanPham.getCategory());
        }
        if (sanPham.getBrand() != null && sanPham.getBrand().getId() == null) {
            thuongHieuRepository.save(sanPham.getBrand());
        }
        if (sanPham.getSole() != null && sanPham.getSole().getId() == null) {
            deRepository.save(sanPham.getSole());
        }

        sanPham = sanPhamRepository.save(sanPham);

        List<String> allImageUrls = new ArrayList<>();

        if (request.getListImages() != null && !request.getListImages().isEmpty()) {
            if (request.getListImages().size() > 3) {
                throw new NgoaiLe("Chỉ được thêm tối đa 3 hình ảnh!");
            }
            allImageUrls.addAll(request.getListImages());
        }

        for (String listImages : allImageUrls) {
            anhRepository.save(
                    Images.builder()
                            .sanPham(sanPham)
                            .name(listImages)
                            .build()
            );
        }

        return sanPhamRepository.save(sanPham);
    }



//    public SanPham update(Long id, SanPhamRequest request) {
//    SanPham name = sanPhamRepository.findById(id).get();
//    if (sanPhamRepository.existsByNameIgnoreCase(request.getName())) {
//        if (name.getName().equals(request.getName())) {
//            return sanPhamRepository.save(sanPhamConvert.convertRequestToEntity(name, request));
//        }
//        throw new NgoaiLe(request.getName() + " đã tồn tại!");
//    } else {
//        return sanPhamRepository.save(sanPhamConvert.convertRequestToEntity(name, request));
//    }
//}

    public SanPham update(Long id, SanPhamRequest request) {
        SanPham existingSanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new NgoaiLe("Sản phẩm không tồn tại"));

        // Kiểm tra tên trùng
        if (sanPhamRepository.existsByNameIgnoreCase(request.getName()) &&
                !existingSanPham.getName().equals(request.getName())) {
            throw new NgoaiLe(request.getName() + " đã tồn tại!");
        }

        // Cập nhật thông tin cơ bản
        SanPham updatedSanPham = sanPhamConvert.convertRequestToEntity(existingSanPham, request);

        // Xử lý ảnh
        if (request.getListImages() != null && !request.getListImages().isEmpty()) {
            // Xóa ảnh cũ (tùy yêu cầu nghiệp vụ)
            anhRepository.deleteBySanPhamId(id);

            // Thêm ảnh mới
            if (request.getListImages().size() > 3) {
                throw new NgoaiLe("Chỉ được thêm tối đa 3 hình ảnh!");
            }

            for (String imageUrl : request.getListImages()) {
                anhRepository.save(
                        Images.builder()
                                .sanPham(updatedSanPham)
                                .name(imageUrl)
                                .build()
                );
            }
        }

        // Lưu và trả về sản phẩm đã cập nhật
        return sanPhamRepository.save(updatedSanPham);
    }



    //    public SanPham delete(Long id) {
//        return null;
//    }
    public SanPham delete(Long id) {
        SanPham sanPham = this.getOne(id);
        sanPham.setDeleted(!sanPham.getDeleted());
        return sanPhamRepository.save(sanPham);
    }


}
