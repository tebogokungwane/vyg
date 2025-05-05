package com.vyg.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchRequest {
    private String churchName;  // ✅ Church/Branch name
    private String address;  // ✅ Full Address
    private String provinceName;  // ✅ Province name (e.g., "Gauteng")
    private String regionName;  // ✅ Region name (e.g., "Johannesburg")
    private double latitude;
    private double longitude;
}
