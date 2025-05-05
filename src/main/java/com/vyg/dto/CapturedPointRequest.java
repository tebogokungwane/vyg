package com.vyg.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class CapturedPointRequest {
    private int nationId;
    private String capturedBy;
    private LocalDate dateCaptured;
    private Map<Integer, Integer> eventAttendance;
}
