package com.yoda.yodatore.infrastructure.converter;

import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.common.GenCode;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import org.springframework.stereotype.Component;

@Component
public class VoucherConvert{

        public Voucher addconvertRequest(VoucherRequest request){
            Voucher voucher = Voucher.builder()
                    .name(request.getName())
                    .code(GenCode.genCodeByName(request.getName()+request.getPhanTramGiam()))
                    .giaTriToiThieu(request.getGiaTriToiThieu())
                    .phanTramGiam(request.getPhanTramGiam())
                    .soLuong(request.getSoLuong())
                    .trangThai(request.getTrangThai())
                    .loai(request.getLoai())
                    .ngayBatDau(request.getNgayBatDau())
                    .ngayKetThuc(request.getNgayKetThuc())

                    .build();
            return voucher;
        }

        public Voucher convertRequestToEntity(Voucher entity, VoucherRequest request){
            entity.setName(request.getName());
            entity.setGiaTriToiThieu(request.getGiaTriToiThieu());
            entity.setPhanTramGiam(request.getPhanTramGiam());
            entity.setSoLuong(request.getSoLuong());
            entity.setTrangThai(request.getTrangThai());
            entity.setLoai(request.getLoai());
            entity.setCode(GenCode.genCodeByName(request.getName()+request.getPhanTramGiam()));
            entity.setNgayBatDau(request.getNgayBatDau());
            entity.setNgayKetThuc(request.getNgayKetThuc());
            return entity;
        }
}
