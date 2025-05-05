package com.vyg.controller;

import com.vyg.dto.PointAdjustmentRequest;
import com.vyg.service.PointAdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointAdjustmentController {

    private final PointAdjustmentService pointAdjustmentService;


    @PostMapping("/adjust")
    public ResponseEntity<String> adjustPoints(
            @RequestBody PointAdjustmentRequest pointAdjustmentRequest) {
        pointAdjustmentService.adjustPoints(pointAdjustmentRequest);
        return ResponseEntity.ok("Points adjusted successfully");
    }

}
