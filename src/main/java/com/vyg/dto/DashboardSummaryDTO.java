package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryDTO {

    private long totalMembers;
    private long totalMentors;
    private long totalSecretaries;
    private long totalNations;
    private long totalPoints;
    private String topNation;

}
