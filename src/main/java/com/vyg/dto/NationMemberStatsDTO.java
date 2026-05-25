package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NationMemberStatsDTO {
    private Long nationId;
    private Long totalMembers;
    private Long totalMentors;
    private Long totalSecretaries;
}
