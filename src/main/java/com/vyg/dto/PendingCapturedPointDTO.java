package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingCapturedPointDTO {

    private Long id;
    private String nationName;
    private String addressName;
    private String fullAddress;
    private String baseEventName;
    private int numberOfPeople;
    private int points;
    private int totalPoints;
    private LocalDate dateCaptured;
    private String capturedBy;
}

