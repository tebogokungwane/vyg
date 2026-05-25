package com.vyg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {MailSenderAutoConfiguration.class})
@EnableAsync
public class VictoryYouthGroupApplication {

	public static void main(String[] args) {
		SpringApplication.run(VictoryYouthGroupApplication.class, args);
	}

}
