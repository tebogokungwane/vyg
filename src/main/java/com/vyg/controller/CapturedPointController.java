package com.vyg.controller;

import com.vyg.entity.CapturedPoint;
import com.vyg.dto.CapturedPointRequestV2;
import com.vyg.service.CapturedPointServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class CapturedPointController {

    private final CapturedPointServiceImpl capturedPointService;

    @PostMapping("/capture")
    public ResponseEntity<?> capturePoints(@RequestBody CapturedPointRequestV2 request) {
        try {
            capturedPointService.capturePoints(request);
            return ResponseEntity.ok("Points captured successfully");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage())); // ✅ sends a proper JSON error message
        }
    }

    @GetMapping("/summary/address/{addressId}")
    public ResponseEntity<List<CapturedPoint>> getPointsSummaryByAddress(@PathVariable Long addressId) {
        return ResponseEntity.ok(capturedPointService.getNationPointsByAddress(addressId));
    }


    @GetMapping("/top-performance/address/{addressId}")
    public ResponseEntity<Map<String, Object>> getTopNationPerformance(@PathVariable Long addressId) {
        return ResponseEntity.ok(capturedPointService.getTopNationPerformance(addressId));
    }


}
