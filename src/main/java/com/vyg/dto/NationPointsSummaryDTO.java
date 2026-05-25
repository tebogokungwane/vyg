package com.vyg.dto;

import com.vyg.entity.BaseEvent;
import com.vyg.entity.Nations;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NationPointsSummaryDTO {

    private Nations nation;
    private BaseEvent baseEvent;
    private int numberOfPeople;
    private int points;
    private LocalDate dateCaptured;

    // Optional: default constructor if needed by frameworks
    public NationPointsSummaryDTO() {}
}
