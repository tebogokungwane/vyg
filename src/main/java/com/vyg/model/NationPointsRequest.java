package com.vyg.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NationPointsRequest {
    private String nationName;  // ✅ Nation as String (Enum will be converted later)
    private Long eventId;       // ✅ Event ID
    private int weekNumber;     // ✅ Week Number
    private int points;         // ✅ Points Earned
    private String province;    // ✅ Province
    private String region;      // ✅ Region
}
