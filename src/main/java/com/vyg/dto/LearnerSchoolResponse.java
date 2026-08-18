package com.vyg.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LearnerSchoolResponse {

    private Long learnerId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String grade;
    private String className;
    private String status;

    private Long schoolId;
    private String schoolCode;
    private String schoolName;
}