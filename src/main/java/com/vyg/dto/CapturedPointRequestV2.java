package com.vyg.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class CapturedPointRequestV2 {
    private int nationId;
    private String capturedBy;
    private LocalDate dateCaptured;
    private Map<Integer, Integer> eventAttendance;
    private Long addressId; // ✅ NEW FIELD
}
