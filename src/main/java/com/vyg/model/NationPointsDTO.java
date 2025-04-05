package com.vyg.model;

import com.vyg.enumerator.Nation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NationPointsDTO {
    private String nation;
    private int week;
    private String month;
    private int year;
    private int points;

    public NationPointsDTO(Nation nation, int weekNumber) {
    }
}
