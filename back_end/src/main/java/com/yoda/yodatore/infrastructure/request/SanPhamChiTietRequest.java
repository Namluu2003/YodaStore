package com.yoda.yodatore.infrastructure.request;

import com.yoda.yodatore.infrastructure.common.PageableRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@ToString
public class SanPhamChiTietRequest extends PageableRequest{
    private Long id;
    private String code;
    @NotNull(message = "Vui lòng chọn giày!")
    private Long shoe;
    @NotNull(message = "Vui lòng chọn màu sắc!")
    private Long color;
    @NotNull(message = "Vui lòng chọn kích cỡ!")
    private Long size;

    @NotNull(message = "Số lượng không được để trống!")
    private Integer quantity;
    @NotNull(message = "Đơn giá không được để trống!")
    private BigDecimal price;
    @NotNull(message = "Cân nặng không được để trống!")
    private Double weight;
    @NotEmpty(message = "Hình ảnh không được để trống!")
    private List<String> listImages;

    //    filter
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean deleted;
    private String name;
}
