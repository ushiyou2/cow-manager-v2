package com.example.demo.messagingstompwebsocket;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "openai.api")
public class OpenAiConfig {
	
	private String token;

}
