package com.vyg.service;

import com.vyg.entity.Address;
import com.vyg.entity.Schools;
import com.vyg.mapper.SchoolMapper;
import com.vyg.dto.SchoolRequestDTO;
import com.vyg.repository.AddressRepository;
import com.vyg.repository.SchoolsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService{

    private final SchoolsRepository schoolsRepository;
    private final AddressRepository addressRepository;

    @Override
    public Schools saveSchool(SchoolRequestDTO schoolRequestDTO) {

        Address address_id = addressRepository.findById(schoolRequestDTO.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid address ID"));

        return schoolsRepository.save(SchoolMapper.toEntity(schoolRequestDTO, address_id));
    }

    @Override
    public List<Schools> listOfSchools(Long addressId) {
        return schoolsRepository.findSchoolsByAddress_Id(addressId);
    }

    @Override
    public Schools updateSchool(Long id, SchoolRequestDTO dto) {
        Schools school = schoolsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid school ID"));

        school.setSchoolName(dto.getSchoolName());
        school.setSchoolAddress(dto.getSchoolAddress());
        school.setPersonToContact(dto.getPersonToContact());
        school.setContactDetails(dto.getContactDetails());
        school.setMentor(dto.getMentor());

        return schoolsRepository.save(school);
    }



}
