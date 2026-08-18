package com.vyg.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LearnerDTO {

    private String learnerId;
    private String fullName;
    private String grade;
    private String gender;
    private String programmeInterests;
    private Boolean needsMentor;
    private String status;
}
