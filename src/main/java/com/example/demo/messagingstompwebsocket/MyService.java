package com.example.demo.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "openai.api")
@Data
public class MyService {

    @Value("${openai.api.token}")
	private String token;
    
    

}
