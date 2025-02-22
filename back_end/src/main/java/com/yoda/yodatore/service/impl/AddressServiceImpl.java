package com.yoda.yodatore.service.impl;

import com.yoda.yodatore.entity.Address;
import com.yoda.yodatore.infrastructure.converter.AddressConvert;
import com.yoda.yodatore.infrastructure.exception.NgoaiLe;
import com.yoda.yodatore.infrastructure.request.AddressRequest;
import com.yoda.yodatore.infrastructure.response.AddressResponse;
import com.yoda.yodatore.repository.AddressRepository;
import com.yoda.yodatore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressConvert addressConvert;

    @Override
    public Page<AddressResponse> getByAccount(AddressRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return addressRepository.getAddress(request, pageable);
    }


}
