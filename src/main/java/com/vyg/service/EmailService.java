package com.vyg.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Value("${spring.mail.username:noreply@vyg.org}")
    private String fromEmail;

    @Async
    public void sendWelcomeEmail(String to, String name, String password) {
        if (mailSender == null) {
            log.warn("Mail not configured. Welcome email for {} not sent.", to);
            return;
        }
        String subject = "Welcome to Victory Youth Group \uD83C\uDF1F";
        String html = """
                <div style="font-family: 'Segoe UI', Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 30px; background: #ffffff; border-radius: 12px; border: 1px solid #e0e0e0;">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #1a237e; margin: 0;">Victory Youth Group</h1>
                        <p style="color: #5c6bc0; font-size: 14px; margin-top: 5px;">Empowering the Next Generation</p>
                    </div>
                    <h2 style="color: #333;">Welcome, %s! 🎉</h2>
                    <p style="color: #555; line-height: 1.6;">
                        We're thrilled to have you join our community. Your account has been successfully created and you're all set to get started.
                    </p>
                    <div style="background: #f5f7ff; padding: 20px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #3f51b5;">
                        <p style="margin: 5px 0; color: #333;"><strong>Email:</strong> %s</p>
                        <p style="margin: 5px 0; color: #333;"><strong>Temporary Password:</strong> %s</p>
                    </div>
                    <p style="color: #555; line-height: 1.6;">
                        For your security, please log in and change your password immediately.
                    </p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s/login" style="background: #3f51b5; color: #ffffff; padding: 14px 32px; text-decoration: none; border-radius: 6px; font-weight: bold; display: inline-block;">Log In Now</a>
                    </div>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 30px 0;">
                    <p style="color: #999; font-size: 12px; text-align: center;">
                        This is an automated message from Victory Youth Group. Please do not reply to this email.
                    </p>
                </div>
                """.formatted(name, to, password, frontendUrl);
        sendHtmlEmail(to, subject, html);
    }

    @Async
    public void sendResetEmail(String to, String resetLink) {
        if (mailSender == null) {
            log.warn("Mail not configured. Reset email for {} not sent.", to);
            return;
        }
        String subject = "Password Reset Request – Victory Youth Group";
        String html = """
                <div style="font-family: 'Segoe UI', Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 30px; background: #ffffff; border-radius: 12px; border: 1px solid #e0e0e0;">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #1a237e; margin: 0;">Victory Youth Group</h1>
                        <p style="color: #5c6bc0; font-size: 14px; margin-top: 5px;">Password Reset</p>
                    </div>
                    <h2 style="color: #333;">Reset Your Password 🔐</h2>
                    <p style="color: #555; line-height: 1.6;">
                        We received a request to reset the password associated with your account. If you made this request, click the button below to proceed.
                    </p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" style="background: #e53935; color: #ffffff; padding: 14px 32px; text-decoration: none; border-radius: 6px; font-weight: bold; display: inline-block;">Reset Password</a>
                    </div>
                    <p style="color: #555; line-height: 1.6;">
                        If you did not request a password reset, you can safely ignore this email. Your password will remain unchanged.
                    </p>
                    <p style="color: #999; font-size: 13px; margin-top: 20px;">
                        This link will expire in 30 minutes for security purposes.
                    </p>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 30px 0;">
                    <p style="color: #999; font-size: 12px; text-align: center;">
                        This is an automated message from Victory Youth Group. Please do not reply to this email.
                    </p>
                </div>
                """.formatted(resetLink);
        sendHtmlEmail(to, subject, html);
    }

    @Async
    public void sendPasswordChangedEmail(String to, String name) {
        if (mailSender == null) {
            log.warn("Mail not configured. Password changed email for {} not sent.", to);
            return;
        }
        String subject = "Password Updated Successfully – Victory Youth Group";
        String html = """
                <div style="font-family: 'Segoe UI', Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 30px; background: #ffffff; border-radius: 12px; border: 1px solid #e0e0e0;">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="color: #1a237e; margin: 0;">Victory Youth Group</h1>
                        <p style="color: #5c6bc0; font-size: 14px; margin-top: 5px;">Security Notification</p>
                    </div>
                    <h2 style="color: #333;">Password Changed ✅</h2>
                    <p style="color: #555; line-height: 1.6;">
                        Hi %s,
                    </p>
                    <p style="color: #555; line-height: 1.6;">
                        Your password has been successfully updated. You can now use your new password to log in.
                    </p>
                    <div style="background: #fff3e0; padding: 16px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #ff9800;">
                        <p style="margin: 0; color: #e65100; font-weight: bold;">⚠️ Didn't make this change?</p>
                        <p style="margin: 5px 0 0 0; color: #555;">If you did not update your password, please contact your branch leader immediately to secure your account.</p>
                    </div>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s/login" style="background: #3f51b5; color: #ffffff; padding: 14px 32px; text-decoration: none; border-radius: 6px; font-weight: bold; display: inline-block;">Log In</a>
                    </div>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 30px 0;">
                    <p style="color: #999; font-size: 12px; text-align: center;">
                        This is an automated message from Victory Youth Group. Please do not reply to this email.
                    </p>
                </div>
                """.formatted(name, frontendUrl);
        sendHtmlEmail(to, subject, html);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("Email '{}' sent to {}", subject, to);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }
}
