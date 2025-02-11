package com.yoda.yodatore.service.impl;


import com.yoda.yodatore.entity.DanhMuc;
import com.yoda.yodatore.entity.De;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.response.DeResponse;
import com.yoda.yodatore.repository.DeRepository;
import com.yoda.yodatore.service.DeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DeServiceImpl implements DeService {
    @Autowired
    private DeRepository repository;
    public PhanTrang<DeResponse> getAll(String name, Integer page, Boolean status) {
        return new PhanTrang<>(repository.getAll(name, status, PageRequest.of(page - 1, 5)));
    }
    public De getOne(Long id) {
        return repository.findById(id).get();
    }


    public De add(De de) {
        if (repository.existsByNameIgnoreCaseAndNameNot(de.getName(), "")) {
            throw new NgoaiLe("Đế giày " + de.getName() + " đã tồn tại!");
        }
        return repository.save(de);
    }


    public De update(Long id, De de) {
        De name = this.getOne(id);
        if (repository.existsByNameIgnoreCaseAndNameNot(de.getName(), name.getName())) {
            throw new NgoaiLe("Đế giày " + de.getName() + " đã tồn tại!");
        }
        name.setName(de.getName());
        return repository.save(name);
    }


    public De delete(Long id) {
        De name = this.getOne(id);
        name.setDeleted(!name.getDeleted());
        return repository.save(name);
    }
}
