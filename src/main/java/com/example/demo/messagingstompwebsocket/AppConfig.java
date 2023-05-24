package com.example.demo.messagingstompwebsocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.gpt.ChatGPT;

@Configuration
public class AppConfig {
 
    @Bean
    public ChatGPT chatGPT() {
        return new ChatGPT();
    }
}