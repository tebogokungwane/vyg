package com.vyg.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SchoolWithLearnersDTO {

    private String schoolId;
    private String schoolName;
    private String area;
    private String province;
    private String contactTeacherName;
    private String contactTeacherPhone;
    private String contactTeacherEmail;
    private String gradesInvolved;
    private String activeProgrammes;
    private String status;
    private List<LearnerDTO> learners;
}
