package com.vyg.service;

import com.vyg.dto.LearnerDTO;
import com.vyg.dto.RegisterLearnerRequest;
import com.vyg.dto.SchoolWithLearnersDTO;

import java.util.List;

public interface LearnerService {

    List<SchoolWithLearnersDTO> getAllSchoolsWithLearners();

    List<SchoolWithLearnersDTO> getSchoolsWithLearnersByAddress(Long addressId);

    SchoolWithLearnersDTO getSchoolWithLearners(String schoolId);

    LearnerDTO registerLearner(RegisterLearnerRequest request);
}
