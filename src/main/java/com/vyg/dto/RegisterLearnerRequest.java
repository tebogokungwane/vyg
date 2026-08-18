package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterLearnerRequest {

    private String firstName;
    private String lastName;
    private String grade;
    private String gender;
    private String programmeInterests;
    private Boolean needsMentor;
    private String schoolCode;
    private String capturedBy;
}
