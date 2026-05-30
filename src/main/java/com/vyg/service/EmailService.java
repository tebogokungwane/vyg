package com.vyg.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Async
    public void sendWelcomeEmail(String to, String name, String password) {
        if (mailSender == null) {
            log.warn("Mail not configured. Welcome email for {} not sent.", to);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Welcome to Victory Youth Group");
            message.setText(
                    "Hi " + name + ",\n\n" +
                    "Your account has been created successfully.\n\n" +
                    "Email: " + to + "\n" +
                    "Password: " + password + "\n\n" +
                    "Login here: " + frontendUrl + "/login\n\n" +
                    "Please log in and change your password.\n\n" +
                    "Regards,\nVictory Youth Group"
            );
            mailSender.send(message);
            log.info("Welcome email sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendResetEmail(String to, String resetLink) {
        if (mailSender == null) {
            log.warn("Mail not configured. Reset email for {} not sent.", to);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Password Reset Request");
            message.setText("Click the link below to reset your password:\n\n" + resetLink);
            mailSender.send(message);
            log.info("Reset email sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send reset email to {}: {}", to, e.getMessage());
        }
    }
}
