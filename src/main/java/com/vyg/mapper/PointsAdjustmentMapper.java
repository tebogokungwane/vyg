package com.vyg.mapper;

import com.vyg.dto.PointAdjustmentRequest;
import com.vyg.entity.Address;
import com.vyg.entity.Nations;
import com.vyg.entity.PointAdjustment;
import com.vyg.enumerator.AdjustmentType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PointsAdjustmentMapper {

    public static PointAdjustment toEntity(PointAdjustmentRequest request, Address address, Nations nation) {
        return PointAdjustment.builder()
                .points(request.getPoints())
                .adjustmentType(AdjustmentType.valueOf(request.getAdjustmentType()))
                .reason(request.getReason())
                .capturedBy(request.getCapturedBy())
                .dateCaptured(LocalDate.now())
                .address(address)
                .nation(nation)
                .build();

    }

}
