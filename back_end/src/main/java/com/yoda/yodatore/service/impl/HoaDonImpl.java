package com.yoda.yodatore.service.impl;


import com.yoda.yodatore.entity.HoaDon;
import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.converter.HoaDonConverter;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.HoaDonRequest;
import com.yoda.yodatore.infrastructure.response.HoaDonResponse;
import com.yoda.yodatore.repository.HoaDonRepository;
import com.yoda.yodatore.service.HoaDonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HoaDonImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository repository;

    @Autowired
    private HoaDonConverter converter;

    @Override
    public PhanTrang<HoaDonResponse> getAll(HoaDonRequest request){
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PhanTrang<>(repository.getAllHoaDon(request,pageable));
    }

    @Override
    public HoaDon getOne(Long id){
        return repository.findById(id).get();
    }

    @Override
    public HoaDon add(HoaDonRequest request){
        if (repository.existsByCodeIgnoreCase(request.getCode())){
            throw new NgoaiLe("Hóa Đơn /'" + request.getCode() + "/' đã tồn tại!");
        }
        HoaDon hoaDon = converter.addconvertRequest(request);
        return repository.save(hoaDon);
    }

    @Override
    public HoaDon update(Long id, HoaDonRequest request) {
        HoaDon hoaDon = repository.findById(id)
                .orElseThrow(() -> new NgoaiLe("Không tìm thấy hóa đơn với ID: " + id));

        // Kiểm tra nếu code thay đổi và đã tồn tại
        if (!hoaDon.getCode().equalsIgnoreCase(request.getCode()) &&
                repository.existsByCodeIgnoreCase(request.getCode())) {
            throw new NgoaiLe("Hóa Đơn " + request.getCode() + " đã tồn tại!");
        }

        return repository.save(converter.convertRequestToEntity(hoaDon, request));
    }


    @Override
    public HoaDon delete(Long id) {
        HoaDon hoaDon = this.getOne(id);
        hoaDon.setDeleted(!hoaDon.getDeleted());
        return repository.save(hoaDon);
    }
}
