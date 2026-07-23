package com.vyg.mapper;

import com.vyg.entity.Address;
import com.vyg.entity.SchoolInstitution;
import com.vyg.entity.Schools;
import com.vyg.dto.SchoolRequestDTO;

import java.time.LocalDateTime;

public class SchoolMapper {

    public static Schools toEntity(SchoolRequestDTO schoolRequestDTO, Address address) {
        return toEntity(schoolRequestDTO, address, null);
    }

    public static Schools toEntity(SchoolRequestDTO schoolRequestDTO, Address address, SchoolInstitution institution) {

        Schools schools = new Schools();
        schools.setSchoolName(schoolRequestDTO.getSchoolName());
        schools.setSchoolAddress(schoolRequestDTO.getSchoolAddress());
        schools.setPersonToContact(schoolRequestDTO.getPersonToContact());
        schools.setContactDetails(schoolRequestDTO.getContactDetails());
        schools.setMentor(schoolRequestDTO.getMentor());
        schools.setCreateBy(schoolRequestDTO.getCreateBy());
        schools.setAddress(address);
        schools.setSchoolInstitution(institution);
        schools.setDateCreated(LocalDateTime.now());

        // New contact fields
        schools.setContactTeacherName(schoolRequestDTO.getContactTeacherName());
        schools.setContactTeacherPhone(schoolRequestDTO.getContactTeacherPhone());
        schools.setContactTeacherEmail(schoolRequestDTO.getContactTeacherEmail());
        schools.setPrincipalName(schoolRequestDTO.getPrincipalName());
        schools.setPrincipalPhone(schoolRequestDTO.getPrincipalPhone());
        schools.setPrincipalEmail(schoolRequestDTO.getPrincipalEmail());

        return schools;
    }
}
