package com.spring.web.service.impl;

import com.spring.web.model.Address;
import com.spring.web.model.User;
import com.spring.web.repository.AddressRepository;
import com.spring.web.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AddressService implements IAddressService {
    @Autowired
    private AddressRepository repository;
    @Override
    public Optional<Address> findById(Long aLong) {

        return repository.findById(aLong);
    }

    @Override
    public List<Address> findAll() {

       return repository.findAll();
    }

    @Override
    public Address save(Address address) {

        return repository.save(address);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);
    }

    public Address saveInfoAddress(Address address){
        Address address1 = repository.save(address);
        address1.setProvince(address.getProvince());
        address1.setDistrict(address.getDistrict());
        address1.setWard(address.getWard());
        return repository.save(address);
    }
}
