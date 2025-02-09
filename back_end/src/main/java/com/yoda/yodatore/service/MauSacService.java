package com.yoda.yodatore.service;

import com.yoda.yodatore.entity.MauSac;

import com.yoda.yodatore.infrastructure.common.PhanTrang;
import com.yoda.yodatore.infrastructure.response.MauSacResponse;


public interface MauSacService {
    PhanTrang<MauSacResponse> getAll(String name, Integer page, Boolean status);

    MauSac getOne(Long id);
    MauSac add(MauSac mauSac);
    MauSac update(Long id, MauSac mauSac);
    MauSac delete(Long id);
}
