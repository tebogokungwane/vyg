package com.vyg.service;

import com.vyg.entity.Address;
import com.vyg.entity.SchoolInstitution;
import com.vyg.entity.Schools;
import com.vyg.mapper.SchoolMapper;
import com.vyg.dto.SchoolRequestDTO;
import com.vyg.repository.AddressRepository;
import com.vyg.repository.SchoolInstitutionRepository;
import com.vyg.repository.SchoolsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService{

    private final SchoolsRepository schoolsRepository;
    private final AddressRepository addressRepository;
    private final SchoolInstitutionRepository schoolInstitutionRepository;

    @Override
    public Schools saveSchool(SchoolRequestDTO schoolRequestDTO) {

        Address address_id = addressRepository.findById(schoolRequestDTO.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid address ID"));

        SchoolInstitution institution = null;
        if (schoolRequestDTO.getSchoolInstitutionId() != null) {
            institution = schoolInstitutionRepository.findById(schoolRequestDTO.getSchoolInstitutionId())
                    .orElse(null);
        }

        return schoolsRepository.save(SchoolMapper.toEntity(schoolRequestDTO, address_id, institution));
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
        school.setContactTeacherName(dto.getContactTeacherName());
        school.setContactTeacherPhone(dto.getContactTeacherPhone());
        school.setContactTeacherEmail(dto.getContactTeacherEmail());
        school.setPrincipalName(dto.getPrincipalName());
        school.setPrincipalPhone(dto.getPrincipalPhone());
        school.setPrincipalEmail(dto.getPrincipalEmail());

        if (dto.getSchoolInstitutionId() != null) {
            SchoolInstitution institution = schoolInstitutionRepository.findById(dto.getSchoolInstitutionId())
                    .orElse(null);
            school.setSchoolInstitution(institution);
        }

        return schoolsRepository.save(school);
    }

}
