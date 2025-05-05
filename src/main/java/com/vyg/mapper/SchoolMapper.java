package com.vyg.mapper;

import com.vyg.entity.Address;
import com.vyg.entity.Schools;
import com.vyg.dto.SchoolRequestDTO;

import java.time.LocalDateTime;

public class SchoolMapper {

    public static Schools toEntity(SchoolRequestDTO schoolRequestDTO, Address address){

        Schools schools = new Schools();
        schools.setSchoolName(schoolRequestDTO.getSchoolName());
        schools.setSchoolAddress(schoolRequestDTO.getSchoolAddress());
        schools.setPersonToContact(schoolRequestDTO.getPersonToContact());
        schools.setContactDetails(schoolRequestDTO.getContactDetails());
        schools.setMentor(schoolRequestDTO.getMentor());
        schools.setCreateBy(schoolRequestDTO.getCreateBy());
        schools.setAddress(address);
        schools.setDateCreated(LocalDateTime.now());

        return schools;
    }

}
