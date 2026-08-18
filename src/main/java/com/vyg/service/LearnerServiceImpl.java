package com.vyg.service;

import com.vyg.dto.LearnerDTO;
import com.vyg.dto.RegisterLearnerRequest;
import com.vyg.dto.SchoolWithLearnersDTO;
import com.vyg.entity.Schools;
import com.vyg.entity.VygLearner;
import com.vyg.entity.VygSchool;
import com.vyg.repository.SchoolsRepository;
import com.vyg.repository.VygLearnerRepository;
import com.vyg.repository.VygSchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LearnerServiceImpl implements LearnerService {

    private final VygSchoolRepository vygSchoolRepository;
    private final VygLearnerRepository vygLearnerRepository;
    private final SchoolsRepository schoolsRepository;

    @Override
    public LearnerDTO registerLearner(RegisterLearnerRequest request) {
        // Find or create the VygSchool entry for this schoolCode
        VygSchool school = vygSchoolRepository.findById(request.getSchoolCode()).orElse(null);

        if (school == null) {
            // School might be a registered school — find by schoolCode and create a VygSchool entry
            Schools registeredSchool = schoolsRepository.findBySchoolCode(request.getSchoolCode())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "School not found with code: " + request.getSchoolCode()));

            school = new VygSchool();
            school.setSchoolId(registeredSchool.getSchoolCode());
            school.setSchoolName(registeredSchool.getSchoolName());
            school.setArea(registeredSchool.getSchoolAddress());
            school.setProvince("");
            school.setContactTeacherName(registeredSchool.getPersonToContact());
            school.setContactTeacherPhone(registeredSchool.getContactDetails());
            school.setContactTeacherEmail("");
            school.setStatus(registeredSchool.getStatus());
            school.setAddress(registeredSchool.getAddress());
            school = vygSchoolRepository.save(school);
        }

        // Build and save the learner
        VygLearner learner = new VygLearner();
        learner.setLearnerId(UUID.randomUUID().toString().substring(0, 8));
        learner.setFullName(request.getFirstName() + " " + request.getLastName());
        learner.setGrade(request.getGrade());
        learner.setGender(request.getGender());
        learner.setProgrammeInterests(request.getProgrammeInterests());
        learner.setNeedsMentor(request.getNeedsMentor() != null ? request.getNeedsMentor() : true);
        learner.setStatus("Active");
        learner.setSchool(school);

        VygLearner saved = vygLearnerRepository.save(learner);

        return mapLearnerToDTO(saved);
    }

    @Override
    public List<SchoolWithLearnersDTO> getAllSchoolsWithLearners() {
        List<SchoolWithLearnersDTO> result = new ArrayList<>();

        // VygSchool entries (seeded schools with learners)
        List<VygSchool> vygSchools = vygSchoolRepository.findAll();
        vygSchools.stream().map(this::mapVygSchoolToDTO).forEach(result::add);

        // Registered schools that DON'T already have a VygSchool entry
        List<Schools> registeredSchools = schoolsRepository.findAll();
        List<String> vygSchoolIds = vygSchools.stream().map(VygSchool::getSchoolId).toList();
        registeredSchools.stream()
                .filter(s -> !vygSchoolIds.contains(s.getSchoolCode()))
                .map(this::mapRegisteredSchoolToDTO)
                .forEach(result::add);

        return result;
    }

    @Override
    public List<SchoolWithLearnersDTO> getSchoolsWithLearnersByAddress(Long addressId) {
        List<SchoolWithLearnersDTO> result = new ArrayList<>();

        // VygSchool entries for this address
        List<VygSchool> vygSchools = vygSchoolRepository.findByAddress_Id(addressId);
        vygSchools.stream().map(this::mapVygSchoolToDTO).forEach(result::add);

        // Registered schools for this address that DON'T already have a VygSchool entry
        List<Schools> registeredSchools = schoolsRepository.findSchoolsByAddress_Id(addressId);
        List<String> vygSchoolIds = vygSchools.stream().map(VygSchool::getSchoolId).toList();
        registeredSchools.stream()
                .filter(s -> !vygSchoolIds.contains(s.getSchoolCode()))
                .map(this::mapRegisteredSchoolToDTO)
                .forEach(result::add);

        return result;
    }

    @Override
    public SchoolWithLearnersDTO getSchoolWithLearners(String schoolId) {
        VygSchool school = vygSchoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("School not found with id: " + schoolId));
        return mapVygSchoolToDTO(school);
    }

    private SchoolWithLearnersDTO mapVygSchoolToDTO(VygSchool school) {
        List<LearnerDTO> learnerDTOs = school.getLearners() != null
                ? school.getLearners().stream().map(this::mapLearnerToDTO).toList()
                : Collections.emptyList();

        return SchoolWithLearnersDTO.builder()
                .schoolId(school.getSchoolId())
                .schoolName(school.getSchoolName())
                .area(school.getArea())
                .province(school.getProvince())
                .contactTeacherName(school.getContactTeacherName())
                .contactTeacherPhone(school.getContactTeacherPhone())
                .contactTeacherEmail(school.getContactTeacherEmail())
                .gradesInvolved(school.getGradesInvolved())
                .activeProgrammes(school.getActiveProgrammes())
                .status(school.getStatus())
                .learners(learnerDTOs)
                .build();
    }

    private SchoolWithLearnersDTO mapRegisteredSchoolToDTO(Schools school) {
        return SchoolWithLearnersDTO.builder()
                .schoolId(school.getSchoolCode())
                .schoolName(school.getSchoolName())
                .area(school.getSchoolAddress())
                .province("")
                .contactTeacherName(school.getPersonToContact())
                .contactTeacherPhone(school.getContactDetails())
                .contactTeacherEmail("")
                .gradesInvolved("")
                .activeProgrammes("")
                .status(school.getStatus())
                .learners(Collections.emptyList())
                .build();
    }

    private LearnerDTO mapLearnerToDTO(VygLearner learner) {
        return LearnerDTO.builder()
                .learnerId(learner.getLearnerId())
                .fullName(learner.getFullName())
                .grade(learner.getGrade())
                .gender(learner.getGender())
                .programmeInterests(learner.getProgrammeInterests())
                .needsMentor(learner.getNeedsMentor())
                .status(learner.getStatus())
                .build();
    }
}
