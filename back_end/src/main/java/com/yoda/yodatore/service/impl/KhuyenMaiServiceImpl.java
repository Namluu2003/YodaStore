package com.yoda.yodatore.service.impl;


import com.yoda.yodatore.entity.KhuyenMai;
import com.yoda.yodatore.entity.KhuyenMaiChiTiet;
import com.yoda.yodatore.entity.SanPhamChiTiet;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.common.ResponseObject;
import com.yoda.yodatore.infrastructure.converter.KhuyenMaiConver;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.KhuyenMaiRequest;
import com.yoda.yodatore.infrastructure.response.KhuyenMaiResponse;
import com.yoda.yodatore.repository.KhuyenMaiChiTietRepository;
import com.yoda.yodatore.repository.KhuyenMaiRepository;
import com.yoda.yodatore.repository.SanPhamChiTietRepository;
import com.yoda.yodatore.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KhuyenMaiServiceImpl implements KhuyenMaiService {
    @Autowired
    private KhuyenMaiRepository khuyenMaiRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private KhuyenMaiChiTietRepository khuyenMaiChiTietRepository;

    @Autowired
    private KhuyenMaiConver khuyenMaiConver;


    public PhanTrang<KhuyenMaiResponse> getAll(KhuyenMaiRequest request) {
        return new PhanTrang<>(khuyenMaiRepository.getAllPromotion(request, PageRequest.of(request.getPage()-1,request.getSizePage())));
    }
    public boolean isCodeUnique(String code) {
        Optional<KhuyenMai> existingPromotion = khuyenMaiRepository.findByCode(code);
        return existingPromotion.isEmpty();
    }
    public void updateStatusPromotion() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<KhuyenMai> khuyenMais = khuyenMaiRepository.findAll();
        for (KhuyenMai promotion : khuyenMais) {
            LocalDateTime startDate = promotion.getStartDate();
            LocalDateTime endDate = promotion.getEndDate();
            if (currentDateTime.isBefore(startDate)) {
                promotion.setStatus(0); // Chưa bắt đầu
            } else if (currentDateTime.isAfter(startDate) && currentDateTime.isBefore(endDate)) {
                promotion.setStatus(1); // Đang diễn ra
//                promotion.setDeleted(null);
            } else {
                promotion.setStatus(2); // Đã kết thúc
//                promotion.setDeleted(true);
            }
            if (endDate.isEqual(startDate)) {
                promotion.setStatus(2); // Đã kết thúc
//                promotion.setDeleted(true);
            }
            khuyenMaiRepository.save(promotion);
        }
    }

    @Transactional(rollbackFor = NgoaiLe.class)
    public ResponseObject create(KhuyenMaiRequest request) {
        if (request.getCode().length() > 20) {
            throw new NgoaiLe("Mã đợt giảm giá không được vượt quá 20 kí tự.");
        }
        if (!isCodeUnique(request.getCode())) {
            throw new NgoaiLe("Mã đợt giảm giá đã tồn tại");
        }
        if (request.getName().length() > 50) {
            throw new NgoaiLe("Tên đợt giảm giá không được vượt quá 50 kí tự.");
        }
        if(request.getValue() < 1 || request.getValue() >50){
            throw new NgoaiLe("Vui lòng nhập giá trị (%) hợp lệ!");
        }
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new NgoaiLe("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
        }
        if (request.getStartDate().isEqual(request.getEndDate())) {
            throw new NgoaiLe("Ngày giờ bắt đầu không được trùng với ngày giờ kết thúc.");
        }
        if (request.getStartDate().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
            throw new NgoaiLe("Ngày bắt đầu phải từ ngày hiện tại trở đi.");
        }
        KhuyenMai promotionSave = khuyenMaiRepository.save(khuyenMaiConver.convertRequestToEntity(request));
        for (Long x: request.getProductDetails()) {
            KhuyenMaiChiTiet check = khuyenMaiChiTietRepository.findByShoeDetailId(x);
            if(check != null) {
                khuyenMaiChiTietRepository.delete(check);
            }
        }
        for (Long x: request.getProductDetails()) {
            SanPhamChiTiet shoeDetail = sanPhamChiTietRepository.findById(x).get();
            KhuyenMaiChiTiet khuyenMaiChiTiet = new KhuyenMaiChiTiet();
            khuyenMaiChiTiet.setPromotion(promotionSave);
            khuyenMaiChiTiet.setShoeDetail(shoeDetail);
            khuyenMaiChiTiet.setPromotionPrice(shoeDetail.getPrice().subtract((shoeDetail.getPrice().divide(new BigDecimal("100"))).multiply(new BigDecimal(request.getValue()))));
            khuyenMaiChiTietRepository.save(khuyenMaiChiTiet);
        }
        updateStatusPromotion();
        return new ResponseObject(request);
    }


    public KhuyenMaiResponse getOne(Long id) {
        return khuyenMaiRepository.getOnePromotion(id);
    }
    public List<Long> getListIdShoePromotion(Long idPromotion) {
        return khuyenMaiChiTietRepository.getListIdShoePromotion(idPromotion).stream()
                .flatMap(ids -> Arrays.stream(ids.split(",")))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    public List<Long> getListIdShoeDetailInPromotion(Long idPromotion) {
        return khuyenMaiChiTietRepository.getListIdShoeDetailInPromotion(idPromotion).stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
    public KhuyenMai updateEndDate(Long id) {
        KhuyenMai promotionToUpdate = khuyenMaiRepository.findById(id).orElse(null);
        LocalDateTime currentDate = LocalDateTime.now();
        if(promotionToUpdate.getStatus()==2) {
            throw new NgoaiLe("Đợt giảm giá này đã kết thúc rồi!");
        }
        if(promotionToUpdate.getStatus()==0){
            LocalDateTime startDate = currentDate.with(LocalTime.MIN);
            promotionToUpdate.setStartDate(startDate);
        }
        promotionToUpdate.setEndDate(currentDate);
        promotionToUpdate.setStatus(2); // Đã kết thúc
        return khuyenMaiRepository.save(promotionToUpdate);
    }

    public void deleteAll(Long idPromotion) {
        khuyenMaiChiTietRepository.deleteAllById(khuyenMaiChiTietRepository.findIdsByPromotionId(idPromotion));
    }

    public void updateStatus(KhuyenMai promotion) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startDate = promotion.getStartDate();
        LocalDateTime endDate = promotion.getEndDate();
        if (currentDate.isBefore(startDate)) {
            promotion.setStatus(0); // Chưa bắt đầu
        } else if (currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
            promotion.setStatus(1); // Đang diễn ra
        } else {
            promotion.setStatus(2); // Đã kết thúc
//            promotion.setDeleted(true);
        }
        khuyenMaiRepository.save(promotion);
    }

    @Transactional(rollbackFor = NgoaiLe.class)
    public ResponseObject update(Long id, KhuyenMaiRequest request) {
        deleteAll(id);
        KhuyenMai promotion = khuyenMaiRepository.findById(id).get();

        if (request.getCode().length() > 20) {
            throw new NgoaiLe("Mã đợt giảm giá không được vượt quá 20 kí tự.");
        }

        if(!promotion.getCode().equalsIgnoreCase(request.getCode())){
            if (!isCodeUnique(request.getCode())) {
                throw new NgoaiLe("Mã đợt giảm giá đã tồn tại");
            }
        }
        if (request.getName().length() > 50) {
            throw new NgoaiLe("Tên đợt giảm giá không được vượt quá 50 kí tự.");
        }
        if(request.getValue() < 1 || request.getValue() >50){
            throw new NgoaiLe("Vui lòng nhập giá trị (%) hợp lệ!");
        }
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new NgoaiLe("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
        }
        if (request.getStartDate().isEqual(request.getEndDate())) {
            throw new NgoaiLe("Ngày giờ bắt đầu không được trùng với ngày giờ kết thúc.");
        }


        KhuyenMai promotionSave = khuyenMaiRepository.save(khuyenMaiConver.convertRequestToEntity(promotion, request));
        for (Long x: request.getProductDetails()) {
            KhuyenMaiChiTiet check = khuyenMaiChiTietRepository.findByShoeDetailId(x);
            if(check != null) {
                updateStatus(promotion);
                khuyenMaiChiTietRepository.delete(check);
            }
        }
        for (Long x: request.getProductDetails()) {
            SanPhamChiTiet shoeDetail = sanPhamChiTietRepository.findById(x).get();
            KhuyenMaiChiTiet promotionDetail = new KhuyenMaiChiTiet();
            promotionDetail.setPromotion(promotionSave);
            promotionDetail.setShoeDetail(shoeDetail);
            promotionDetail.setPromotionPrice(shoeDetail.getPrice().subtract((shoeDetail.getPrice().divide(new BigDecimal("100"))).multiply(new BigDecimal(request.getValue()))));
            khuyenMaiChiTietRepository.save(promotionDetail);
        }
        return new ResponseObject(promotion);
    }

}
