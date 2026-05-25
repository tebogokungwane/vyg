package com.vyg.dto;

import lombok.Data;

@Data
public class ManualAdjustmentRequestDTO {
    private Long nationId;
    private Long addressId;   // ✅ ADD THIS
    private Integer points;
    private String reason;
    private String requestedBy;
}

