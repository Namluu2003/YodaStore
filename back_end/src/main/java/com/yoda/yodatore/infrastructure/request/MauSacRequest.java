package com.yoda.yodatore.infrastructure.request;
import com.yoda.yodatore.infrastructure.common.PageableRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MauSacRequest extends PageableRequest {
    private Long id;
    @NotEmpty(message = "Màu sắc không được để trống")
    private String name;
    private Boolean status;
}
