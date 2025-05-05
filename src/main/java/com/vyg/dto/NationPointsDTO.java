package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NationPointsDTO {
    private String nation;
    private int week;
    private String month;
    private int year;
    private int points;
}
