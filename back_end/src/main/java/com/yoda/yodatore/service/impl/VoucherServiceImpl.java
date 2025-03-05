package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.Voucher;
import com.yoda.yodatore.infrastructure.common.GenCode;
import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.constant.Message;
import com.yoda.yodatore.infrastructure.converter.VoucherConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.VoucherRequest;
import com.yoda.yodatore.infrastructure.response.VoucherResponse;
import com.yoda.yodatore.repository.VoucherRepository;
import com.yoda.yodatore.service.VoucherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherConvert voucherConvert;


    public PhanTrang<VoucherResponse> getAll(VoucherRequest request) {
        return new PhanTrang<>(voucherRepository.getAllVoucher(request, PageRequest.of(request.getPage()-1 > 0 ? request.getPage()-1 : 0, request.getSizePage())));
    }

    public Voucher getOne(Long id) {
        for (Voucher voucher : voucherRepository.findAll()) {
            updateStatus(voucher);
        }
        return voucherRepository.findById(id).get();
    }
    @Transactional
    public Voucher add(VoucherRequest request) {
        String randomCodeVoucher = GenCode.randomCodeVoucher();
        String code = request.getCode();

        // Kiểm tra mã request đã tồn tại hay chưa
        if (voucherRepository.existsByCode(code)) {
            throw new NgoaiLe(Message.CODE_EXISTS);
//            throw new RestApiException("Mã đã tồn tại");
        }
        if (request.getCode().length() > 20) {
            throw new NgoaiLe("Mã Voucher không được vượt quá 20 kí tự.");
        }
        if (request.getName().length() > 50) {
            throw new NgoaiLe("Tên Voucher không được vượt quá 50 kí tự.");
        }
        if (request.getQuantity() <= 0) {
            throw new NgoaiLe("Số lượng phải lớn hơn 0. ");
        }
        if (request.getQuantity() <= 0 || request.getQuantity() != (int) request.getQuantity() || request.getQuantity() == null) {
            throw new NgoaiLe("Số lượng phải là số nguyên dương.");
        }

//        if (request.getPercentReduce() < 0 || request.getPercentReduce() > 100) {
//            throw new RestApiException("Phần trăm giảm phải nằm trong khoảng từ 0 đến 100");
//        }

        try {
            float percentReduce = Float.valueOf(request.getPercentReduce());

            if (percentReduce < 0 || percentReduce > 100) {
                throw new NgoaiLe("Phần trăm giảm phải nằm trong khoảng từ 0 đến 100. ");
            }

        } catch (NumberFormatException e) {

            throw new NgoaiLe("Phần trăm giảm phải nằm trong khoảng từ 0 đến 100. ");

        } catch (NgoaiLe e) {
            throw e;
        }

        if (Float.valueOf(request.getPercentReduce()) <= 0) {
            throw new NgoaiLe("Phần trăm giảm phải lớn hơn 0. ");
        }
        if (request.getMinBillValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NgoaiLe("Đơn tối thiểu phải lớn hơn hoặc bằng 0. ");
        }
        if (request.getMaxBillValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NgoaiLe("Đơn tối đa phải lớn hơn hoặc bằng 0. ");
        }
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new NgoaiLe("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
        }
        if (request.getStartDate().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
            throw new NgoaiLe("Ngày bắt đầu phải từ ngày hiện tại trở đi.");
        }
        if (request.getStartDate().isEqual(request.getEndDate())) {
            throw new NgoaiLe("Ngày giờ bắt đầu không được trùng với ngày giờ kết thúc.");
        }

//        if (request.getStartDate().isAfter(request.getEndDate())) {
//            throw new RestApiException("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
//        }
        Voucher voucher = voucherConvert.converRequestToEntity(request);
        voucher.setCode(randomCodeVoucher);

        updateStatus(voucher);
//        voucherRepository.save(voucher);
        return voucher;

    }


    public Voucher update(Long id, VoucherRequest request) {
        Voucher voucherToUpdate = voucherRepository.findById(id).orElse(null);
        if (voucherToUpdate == null) {
            throw new NgoaiLe("Không tìm thấy voucher có id: " + id);
        }

        if (!voucherToUpdate.getCode().equals(request.getCode())) {
            // Kiểm tra mã Voucher đã tồn tại hay chưa
            if (voucherRepository.existsByCode(request.getCode())) {
                throw new NgoaiLe(Message.CODE_EXISTS);
//            throw new RestApiException("Mã đã tồn tại");
            }
        }
        if (request.getCode().length() > 20) {
            throw new NgoaiLe("Mã Voucher không được vượt quá 20 kí tự.");
        }
        if (request.getName().length() > 50) {
            throw new NgoaiLe("Tên Voucher không được vượt quá 50 kí tự.");
        }
        if (request.getQuantity() <= 0) {
            throw new NgoaiLe("Số lượng phải lớn hơn 0. ");
        }
        if (request.getQuantity() <= 0 || request.getQuantity() != (int) request.getQuantity() || request.getQuantity() == null) {
            throw new NgoaiLe("Số lượng phải là số nguyên dương.");
        }

        if (Float.valueOf(request.getPercentReduce()) < 0 || Float.valueOf(request.getPercentReduce()) > 100) {
            throw new NgoaiLe("Phần trăm giảm phải nằm trong khoảng từ 0 đến 100");
        }
//        if(Float.valueOf(request.getPercentReduce())<0 || Float.valueOf(request.getPercentReduce())){
//
//        }

//        try {
//            float percentReduce = Float.valueOf(request.getPercentReduce());
//
//            if (percentReduce < 0 || percentReduce > 100) {
//                throw new RestApiException("Phần trăm giảm phải nằm trong khoảng từ 0 đến 100. ");
//            }
//        } catch (NumberFormatException e) {
//
//            throw new RestApiException("Phần trăm giảm phải nằm trong khoảng từ 0 đến 100. ");
//
//        } catch (RestApiException e) {
//            throw e;
//        }
        if (!String.valueOf(request.getPercentReduce()).matches("^-?\\d+(\\.\\d+)?$")) {
            System.out.println("1212");
            throw new NgoaiLe("Phần trăm giảm phải là số");
        }
        if (request.getMinBillValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NgoaiLe("Đơn tối thiểu phải lớn hơn hoặc bằng 0. ");
        }
        if (request.getMaxBillValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NgoaiLe("Đơn tối đa phải lớn hơn hoặc bằng 0. ");
        }
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new NgoaiLe("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
        }
        Voucher voucherSave = voucherRepository.save(voucherConvert.convertRequestToEntity(id, request));

//        voucherToUpdate.setName(voucher.getName());
//        voucherToUpdate.setCode(voucher.getCode());
//        voucherToUpdate.setQuantity(voucher.getQuantity());
//        voucherToUpdate.setMinBillValue(voucher.getMinBillValue());
//        voucherToUpdate.setPercentReduce(voucher.getPercentReduce());
//        voucherToUpdate.setStartDate(voucher.getStartDate());
//        voucherToUpdate.setEndDate(voucher.getEndDate());
        if (voucherSave != null) {
            updateStatus(voucherToUpdate);
//            voucherRepository.save(voucherToUpdate);
        }


        return voucherSave;
    }

    public String delete(Long id) {
        voucherRepository.deleteById(id);
        return "Xóa oke";


    }

    public boolean isVoucherCodeExists(String code) {
        return voucherRepository.existsByCode(code);
    }

    public void updateStatus() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Voucher> vouchers = voucherRepository.findAll();

        for (Voucher voucher : vouchers) {
            LocalDateTime startDate = voucher.getStartDate();
            LocalDateTime endDate = voucher.getEndDate();

            if (currentDateTime.isBefore(startDate)) {
                voucher.setStatus(0); // Chưa bắt đầu
            } else if (currentDateTime.isAfter(startDate) && currentDateTime.isBefore(endDate)) {
                voucher.setStatus(1); // Đang diễn ra
            } else {
                voucher.setStatus(2); // Đã kết thúc
                voucher.setDeleted(true);
            }
            voucherRepository.save(voucher);
        }


    }

    public void updateStatus(Voucher voucher) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startDate = voucher.getStartDate();
        LocalDateTime endDate = voucher.getEndDate();

        if (currentDate.isBefore(startDate)) {
            voucher.setStatus(0); // Chưa bắt đầu
        } else if (currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
            voucher.setStatus(1); // Đang diễn ra
        } else {
            voucher.setStatus(2); // Đã kết thúc
            voucher.setDeleted(true);
        }
        voucherRepository.save(voucher);
    }

    public void createScheduledVoucher() {
        // Lấy ngày hiện tại
        LocalDateTime currentDateTime = LocalDateTime.now();

        // set name voucher theo ngày tạo
        String voucherName = "Voucher ngày" + currentDateTime.format(DateTimeFormatter.ofPattern("dd/MM"));

        // Đặt startDate vào 00:00:00 của ngày hiện tại
        LocalDateTime startDate = currentDateTime.with(LocalTime.MIN);

        // Đặt endDate vào 23:59:59 của ngày hiện tại
        LocalDateTime endDate = currentDateTime.with(LocalTime.MAX);

        // logic
        Voucher voucher = new Voucher();
        voucher.setCode(GenCode.randomCodeVoucher());
        voucher.setName(voucherName);
        voucher.setStartDate(startDate);
        voucher.setEndDate(endDate);
        voucher.setMinBillValue(BigDecimal.valueOf(10000));
        voucher.setPercentReduce(Float.valueOf(5));
        voucher.setQuantity(100);
        updateStatus(voucher);
//        System.out.println("Voucher: " + voucherName + "");
    }


    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
