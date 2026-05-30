package com.vyg.config;

import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Configuration
@Conditional(MailConfig.MailConfiguredCondition.class)
@Import(MailSenderAutoConfiguration.class)
public class MailConfig {

    static class MailConfiguredCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String username = context.getEnvironment().getProperty("spring.mail.username", "");
            return !username.isBlank();
        }
    }
}
