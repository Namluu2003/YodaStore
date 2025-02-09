package com.yoda.yodatore.service.impl;
import com.yoda.yodatore.entity.MauSac;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.response.MauSacResponse;
import com.yoda.yodatore.repository.MauSacRepository;
import com.yoda.yodatore.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MauSacImpl implements MauSacService {
    @Autowired
    private MauSacRepository repository;



    public PhanTrang<MauSacResponse> getAll(String name, Integer page, Boolean status){
        return new PhanTrang<>(repository.getAll(name,status, PageRequest.of(page-1 ,5)));
    }
    public MauSac getOne(Long id){
        return repository.findById(id).get();
    }

    public MauSac add(MauSac mauSac){
        if (repository.existsByNameIgnoreCaseAndNameNot(mauSac.getName(),"")){
            throw new NgoaiLe("Thuộc tính " + mauSac.getName() + " đã tồn tại");
        }
        return repository.save(mauSac);
    }

    public MauSac update(Long id, MauSac mauSac){
        MauSac name = this.getOne(id);
        if (repository.existsByNameIgnoreCaseAndNameNot(mauSac.getName(),name.getName())){
            throw new NgoaiLe("Thuộc tính" + mauSac.getName() + "đã tồn tại");
        }
        name.setName(mauSac.getName());
        return repository.save(name);
    }

    public MauSac delete(Long id){
        MauSac mauSac = this.getOne(id);
        mauSac.setDeleted(!mauSac.getDeleted());
        return repository.save(mauSac);
    }

}
