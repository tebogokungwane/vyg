package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NationDTO {
    private Long id;
    private String nation;
    private String imageName;
    private String imageType;
    private String imageUrl;
    private double totalPoints;

}

