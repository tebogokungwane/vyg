package com.vyg.controller;

import com.vyg.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Temporary controller for testing email delivery.
 * Hit: POST /api/test-email with { "to": "your@email.com" }
 */
@RestController
@RequestMapping("/api/test-email")
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Map<String, String>> sendTestEmail(@RequestBody Map<String, String> request) {
        String to = request.get("to");
        if (to == null || to.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Field 'to' is required"));
        }

        emailService.sendWelcomeEmail(to, "Test User", "TestPassword123");
        return ResponseEntity.ok(Map.of(
                "message", "Test email queued for delivery to " + to,
                "note", "Check your inbox (and spam folder). Email is sent async so this response returns immediately."
        ));
    }
}
