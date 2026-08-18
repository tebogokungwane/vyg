package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NearbyMemberDTO {
    private Long id;
    private String name;
    private String surname;
    private String cellNumber;
    private Double latitude;
    private Double longitude;
    private Double distanceKm;
}
