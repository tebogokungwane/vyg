package com.vyg.controller;

import com.vyg.entity.CapturedPoint;
import com.vyg.dto.CapturedPointRequestV2;
import com.vyg.enumerator.ApprovalStatus;
import com.vyg.repository.CapturedPointRepository;
import com.vyg.service.CapturedPointServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class CapturedPointController {

    private final CapturedPointServiceImpl capturedPointService;


    @PostMapping("/capture")
    public ResponseEntity<?> capturePoints(@RequestBody CapturedPointRequestV2 request) {
        try {
            log.info("📥 Capture request received: nationId={}, baseEventId={}, date={}");

                    capturedPointService.capturePoints(request);
            return ResponseEntity.ok("Points captured successfully");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage())); // ✅ sends a proper JSON error message
        }
    }

    // ✅ THIS IS MISSING
    @GetMapping("/pending/address/{addressId}")
    public ResponseEntity<?> getPendingPointsByAddress(
            @PathVariable Long addressId
    ) {
        return ResponseEntity.ok(
                capturedPointService.getPendingPointsByAddress(addressId)
        );
    }

    @DeleteMapping("/reject/{id}")
    public ResponseEntity<Void> rejectCapturedPoint(@PathVariable Long id) {
        capturedPointService.rejectCapturedPoint(id);
        return ResponseEntity.noContent().build();
    }


}
