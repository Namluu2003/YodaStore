package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.KichThuoc;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.response.KichThuocResponse;
import com.yoda.yodatore.repository.KichThuocRepository;

import com.yoda.yodatore.service.KichThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class KichThuocServiceImpl implements KichThuocService {
    @Autowired
    private KichThuocRepository repository;


    public PhanTrang<KichThuocResponse> getAll(String name, Integer page, Boolean status) {
        return new PhanTrang<>(repository.getAll(name, status, PageRequest.of(page - 1, 5)));
    }


    public KichThuoc getOne(Long id) {
        return repository.findById(id).get();
    }


    public KichThuoc add(KichThuoc kichThuoc) {
        if (repository.existsByNameIgnoreCaseAndNameNot(kichThuoc.getName(), "")) {
            throw new NgoaiLe("Thuộc tính " + kichThuoc.getName() + " đã tồn tại!");
        }
        return repository.save(kichThuoc);
    }


    public KichThuoc update(Long id, KichThuoc kichThuoc) {
        KichThuoc old = this.getOne(id);
        if (repository.existsByNameIgnoreCaseAndNameNot(kichThuoc.getName(), old.getName())) {
            throw new NgoaiLe("Thuộc tính " + kichThuoc.getName() + " đã tồn tại!");
        }
        old.setName(kichThuoc.getName());
        return repository.save(old);
    }


    public KichThuoc delete(Long id) {
        KichThuoc kichThuoc = this.getOne(id);
        kichThuoc.setDeleted(!kichThuoc.getDeleted());
        return repository.save(kichThuoc);
    }
}
