package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class PointAdjustmentRequest {
        private Long nationId;
        private int points;
        private String adjustmentType; // "ADD" or "SUBTRACT"
        private String reason;
        private String capturedBy;
        private Long addressId;
    }


