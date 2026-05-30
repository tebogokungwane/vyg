package com.vyg.controller;

import com.vyg.entity.Members;
import com.vyg.repository.MemberRepository;
import com.vyg.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepository;
    private final EmailService emailService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Optional<Members> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            String token = UUID.randomUUID().toString();
            // In production, store token in DB with expiry. For now, send email with reset link.
            String resetLink = "http://localhost:3000/reset-password?token=" + token + "&email=" + email;
            emailService.sendResetEmail(email, resetLink);
        }

        // Always return success to prevent email enumeration
        return ResponseEntity.ok(Map.of("message", "If the email exists, a reset link has been sent."));
    }
}
