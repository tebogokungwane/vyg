package com.vyg.config;

import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MailSenderAutoConfiguration.class)
public class MailConfig {
}
