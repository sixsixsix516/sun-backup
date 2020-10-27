package com.sixsixsix516.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author sun 2020/10/27 11:39
 */
@Configuration
public class MailConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.mail")
	public JavaMailSender javaMailSender() {
		return new JavaMailSenderImpl();
	}

}
