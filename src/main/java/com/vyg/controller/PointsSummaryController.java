package com.vyg.controller;

import com.vyg.dto.NationPointsSummaryDTO;
import com.vyg.service.PointsSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointsSummaryController {

    private final PointsSummaryService pointsSummaryService;

    public PointsSummaryController(PointsSummaryService pointsSummaryService) {
        this.pointsSummaryService = pointsSummaryService;
    }

    @GetMapping("/summary/address/{addressId}")
    public ResponseEntity<List<NationPointsSummaryDTO>> getSummary(
            @PathVariable Long addressId) {

        return ResponseEntity.ok(
                pointsSummaryService.getSummary(addressId)
        );
    }

    @GetMapping("/summary/all")
    public ResponseEntity<List<NationPointsSummaryDTO>> getAllSummaries() {
        return ResponseEntity.ok(pointsSummaryService.getAllSummaries());
    }
}

