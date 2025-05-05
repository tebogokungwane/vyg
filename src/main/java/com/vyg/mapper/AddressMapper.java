package com.vyg.mapper;

import com.vyg.entity.Address;
import com.vyg.enumerator.Province;
import com.vyg.enumerator.Branch;
import com.vyg.dto.AddressDTO;

public class AddressMapper {

    public static AddressDTO toDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setProvince(String.valueOf(address.getProvince()));
        dto.setBranch(String.valueOf(address.getBranch()));
        dto.setFullAddress(address.getFullAddress());
        return dto;
    }

    public static Address toEntity(AddressDTO dto) {
        Address address = new Address();

        if (dto.getProvince() != null && !dto.getProvince().isBlank()) {
            address.setProvince(Province.valueOf(dto.getProvince().toUpperCase()));
        } else {
            throw new IllegalArgumentException("Province cannot be null or empty.");
        }

        if (dto.getBranch() != null && !dto.getBranch().isBlank()) {
            address.setBranch(Branch.valueOf(dto.getBranch().toUpperCase()));
        } else {
            throw new IllegalArgumentException("Branch cannot be null or empty.");
        }
//
//        address.setProvince(Province.valueOf(dto.getProvince()));
//        address.setBranch(Branch.valueOf(dto.getBranch()));
        address.setFullAddress(dto.getFullAddress());
        return address;
    }
}
