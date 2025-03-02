package com.yoda.yodatore.infrastructure.request.bill;

import com.yoda.yodatore.infrastructure.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillSearchRequest extends PageableRequest {
    private Long idStaff;
    private Integer status;
    private String code;
    private Boolean deleted;
}

