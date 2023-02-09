package com.spring.web.service.impl;

import com.spring.web.model.Address;
import com.spring.web.service.IAddressService;

import java.util.List;
import java.util.Optional;

public class AddressService implements IAddressService {
    @Override
    public Optional<Address> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Address> findAll() {
        return null;
    }

    @Override
    public Address save(Address address) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
