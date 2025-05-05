package com.vyg.service;

import com.vyg.entity.Schools;
import com.vyg.dto.SchoolRequestDTO;

import java.util.List;

public interface SchoolService {

    Schools saveSchool(SchoolRequestDTO schoolRequest);

    List<Schools> listOfSchools(Long addressId);

    Schools updateSchool(Long id, SchoolRequestDTO schoolRequestDTO);
}
