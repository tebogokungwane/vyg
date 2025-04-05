package com.vyg.model;

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
//    private <E> points;
    private Long addressId;
    private String createBy;


    // ✅ Region
}
