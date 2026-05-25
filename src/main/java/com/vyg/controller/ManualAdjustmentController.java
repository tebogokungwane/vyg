package com.vyg.controller;

import com.vyg.dto.ManualAdjustmentRequestDTO;
import com.vyg.service.ManualAdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/manual-adjustment")
@RequiredArgsConstructor
public class ManualAdjustmentController {

    @Autowired
    private final ManualAdjustmentService manualAdjustmentService;

    @GetMapping("/pending/{addressId}")
    public ResponseEntity<?> getPendingAdjustments(
            @PathVariable Long addressId
    ) {
        return ResponseEntity.ok(
                manualAdjustmentService.getPendingAdjustments(addressId)
        );
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestAdjustment(
            @RequestBody ManualAdjustmentRequestDTO request
    ) {
        manualAdjustmentService.createRequest(request);
        return ResponseEntity.ok(Map.of(
                "message", "Adjustment request submitted for approval"
        ));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approve(
            @PathVariable Long id,
            @RequestParam String approvedBy
    ) {
        manualAdjustmentService.approve(id, approvedBy);
        return ResponseEntity.ok("Approved");
    }
}

