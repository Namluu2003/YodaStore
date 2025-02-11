package com.yoda.yodatore.infrastructure.converter;


import com.yoda.yodatore.entity.SanPham;
import com.yoda.yodatore.infrastructure.request.SanPhamRequest;
import com.yoda.yodatore.repository.DanhMucRepository;
import com.yoda.yodatore.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Component;

@Component
public class SanPhamConvert {
        @Autowired
        private ThuongHieuRepository thuongHieuRepository;

        @Autowired
        private DanhMucRepository danhMucRepository;

        public SanPham addconvertRequest(SanPhamRequest request){
                SanPham sanPham = SanPham.builder()
                        .name(request.getName())
                        .danhMuc(danhMucRepository.findById(request.getDanh_muc()).get())
                        .thuongHieu(thuongHieuRepository.findById(request.getThuong_hieu()).get())
                        .build();
                return sanPham;
        }
        public SanPham convertRequestToEntity(SanPham sanPham, SanPhamRequest request){
                sanPham.setName(request.getName());
                sanPham.setDanhMuc(danhMucRepository.findById(request.getDanh_muc()).get());
                sanPham.setThuongHieu(thuongHieuRepository.findById(request.getThuong_hieu()).get());

                return sanPham;
        }
}
