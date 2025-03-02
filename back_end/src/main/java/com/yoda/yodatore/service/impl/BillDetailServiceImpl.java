package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.BillDetail;
import com.yoda.yodatore.entity.SanPhamChiTiet;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.BillDetailConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.BillDetailRequest;
import com.yoda.yodatore.infrastructure.response.BillDetailResponse;
import com.yoda.yodatore.repository.IBillDetailRepository;
import com.yoda.yodatore.repository.SanPhamChiTietRepository;
import com.yoda.yodatore.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BillDetailServiceImpl implements BillDetailService {

    @Autowired
    private IBillDetailRepository billDetailRepository;
    @Autowired
    private BillDetailConvert billDetailConvert;
    @Autowired
    private SanPhamChiTietRepository shoeDetailRepository;


    public PhanTrang<BillDetailResponse> getAll(BillDetailRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PhanTrang<>(billDetailRepository.getAllBillDetail(request, pageable));
    }


    public BillDetail getOne(Long id) {
        return billDetailRepository.findById(id).orElse(null);
    }

    public BillDetail create(BillDetailRequest request) {
        BillDetail billDetail = billDetailConvert.convertRequestToEntity(request);
        SanPhamChiTiet shoeDetail = shoeDetailRepository.findByCode(request.getShoeDetail());
        if(request.getQuantity() < 1){
            throw new NgoaiLe("Số lượng phải lớn hơn 1!");
        } else if (request.getQuantity() > shoeDetail.getQuantity()) {
            throw new NgoaiLe("Quá số lượng cho phép!");
        }
        shoeDetail.setQuantity(shoeDetail.getQuantity()-request.getQuantity());
        shoeDetailRepository.save(shoeDetail);
        BillDetail existBillDetail = billDetailRepository.findByShoeDetailCodeAndBillId(request.getShoeDetail(), request.getBill());
        if(existBillDetail != null){
            existBillDetail.setPrice(shoeDetail.getPrice());
            existBillDetail.setQuantity(existBillDetail.getQuantity()+request.getQuantity());
            return billDetailRepository.save(existBillDetail);
        }
        return billDetailRepository.save(billDetail);
    }


    public BillDetail update(Long id, BillDetailRequest request) {
        BillDetail old = billDetailRepository.findById(id).get();
        return billDetailRepository.save(billDetailConvert.convertRequestToEntity(old,request));
    }


    public BillDetail delete(Long id) {
        BillDetail billDetail = billDetailRepository.findById(id).get();
        SanPhamChiTiet shoeDetail = billDetail.getShoeDetail();
        shoeDetail.setQuantity(shoeDetail.getQuantity()+billDetail.getQuantity());
        billDetailRepository.delete(billDetail);
        return billDetail;
    }


    public BillDetail updateQuantity(Long id, Integer newQuantity) {
        BillDetail billDetail = billDetailRepository.findById(id).get();
        SanPhamChiTiet shoeDetail = billDetail.getShoeDetail();
        if(newQuantity > (shoeDetail.getQuantity()+billDetail.getQuantity())){
            throw new NgoaiLe("Quá số lượng cho phép!");
        }
        shoeDetail.setQuantity(shoeDetail.getQuantity()+billDetail.getQuantity()-newQuantity);
        billDetail.setQuantity(newQuantity);
        billDetailRepository.save(billDetail);
        shoeDetailRepository.save(shoeDetail);
        return billDetail;
    }
}

