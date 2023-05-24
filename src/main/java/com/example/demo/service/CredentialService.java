package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration")
@Data
public class CredentialService {

	@Value("${spring.security.oauth2.client.registration.google.clientId}")
	private String clientId;
	
	@Value("${spring.security.oauth2.client.registration.google.clientSecret}")
	private String clientSecret;
}
