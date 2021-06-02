package com.sixsixsix516.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 邮箱配置类
 *
 * @author sun 2020/10/27 11:39
 */
@Configuration
public class MailConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.mail")
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		Properties props = new Properties();
		props.put("mail.smtp.ssl.enable", true);
		javaMailSender.setJavaMailProperties(props);
		return javaMailSender;
	}

}
