package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.KichThuoc;
import com.yoda.yodatore.entity.ThuongHieu;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.response.KichThuocResponse;

import com.yoda.yodatore.infrastructure.response.ThuongHieuResponse;
import com.yoda.yodatore.repository.ThuongHieuRepository;
import com.yoda.yodatore.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {
    @Autowired
    private ThuongHieuRepository repository;


    public PhanTrang<ThuongHieuResponse> getAll(String name, Integer page, Boolean status) {
        return new PhanTrang<>(repository.getAll(name, status, PageRequest.of(page - 1, 5)));
    }


    public ThuongHieu getOne(Long id) {
        return repository.findById(id).get();
    }


    public ThuongHieu add(ThuongHieu thuongHieu) {
        if (repository.existsByNameIgnoreCaseAndNameNot(thuongHieu.getName(), "")) {
            throw new NgoaiLe("Thương hiệu " + thuongHieu.getName() + " đã tồn tại!");
        }
        return repository.save(thuongHieu);
    }


    public ThuongHieu update(Long id, ThuongHieu thuongHieu) {
        ThuongHieu name = this.getOne(id);
        if (repository.existsByNameIgnoreCaseAndNameNot(thuongHieu.getName(), name.getName())) {
            throw new NgoaiLe("Thương hiệu " + thuongHieu.getName() + " đã tồn tại!");
        }
        name.setName(thuongHieu.getName());
        return repository.save(name);
    }


    public ThuongHieu delete(Long id) {
        ThuongHieu thuongHieu = this.getOne(id);
        thuongHieu.setDeleted(!thuongHieu.getDeleted());
        return repository.save(thuongHieu);
    }


}
