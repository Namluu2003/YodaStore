package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.DanhMuc;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.response.DanhMucResponse;
import com.yoda.yodatore.infrastructure.response.DeResponse;
import com.yoda.yodatore.infrastructure.response.KichThuocResponse;
import com.yoda.yodatore.repository.DanhMucRepository;
import com.yoda.yodatore.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DanhMucServieImpl implements DanhMucService {
    @Autowired
    private DanhMucRepository repository;


    public PhanTrang<DanhMucResponse> getAll(String name, Integer page, Boolean status) {
        return new PhanTrang<>(repository.getAll(name, status, PageRequest.of(page - 1, 5)));
    }


    public DanhMuc getOne(Long id) {
        return repository.findById(id).get();
    }


    public DanhMuc add(DanhMuc danhMuc) {
        if (repository.existsByNameIgnoreCaseAndNameNot(danhMuc.getName(), "")) {
            throw new NgoaiLe("Thương hiệu " + danhMuc.getName() + " đã tồn tại!");
        }
        return repository.save(danhMuc);
    }


    public DanhMuc update(Long id, DanhMuc danhMuc) {
        DanhMuc name = this.getOne(id);
        if (repository.existsByNameIgnoreCaseAndNameNot(danhMuc.getName(), name.getName())) {
            throw new NgoaiLe("Danh mục " + danhMuc.getName() + " đã tồn tại!");
        }
        name.setName(danhMuc.getName());
        return repository.save(name);
    }


    public DanhMuc delete(Long id) {
        DanhMuc danhMuc = this.getOne(id);
        danhMuc.setDeleted(!danhMuc.getDeleted());
        return repository.save(danhMuc);
    }
}
