package com.vyg.service;

import com.vyg.entity.Address;
import com.vyg.mapper.AddressMapper;
import com.vyg.dto.AddressDTO;
import com.vyg.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address saveAddress(AddressDTO addressDTO){

        Address address = AddressMapper.toEntity(addressDTO);
        return addressRepository.save(address);
    }
}
