package com.vyg.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NationPointsRequest {

    private String nationName;
    private Long eventId;
    private int weekNumber;
    private String province;
    private String region;
    private String createdBy;
}
