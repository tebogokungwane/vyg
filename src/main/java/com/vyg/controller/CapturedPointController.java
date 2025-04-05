package com.vyg.controller;

import com.vyg.model.CapturedPointRequest;
import com.vyg.service.CapturedPointServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class CapturedPointController {

    private final CapturedPointServiceImpl capturedPointService;

    @PostMapping("/capture")
    public ResponseEntity<?> capturePoints(@RequestBody CapturedPointRequest request) {
        try {
            capturedPointService.capturePoints(request);
            return ResponseEntity.ok("Points captured successfully");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
