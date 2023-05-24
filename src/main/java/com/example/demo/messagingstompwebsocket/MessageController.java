package com.example.demo.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.demo.gpt.ChatGPT;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MessageController {
	
	@Autowired
	private ChatGPT chatGPTservice;


  @MessageMapping("/hello") 
  @SendTo("/topic/greetings")
  public Greeting greeting(String text) throws Exception {
	String response = chatGPTservice.chatGPT(text);
    Thread.sleep(1000); // simulated delay
    return new Greeting(HtmlUtils.htmlEscape(response));
  }

}
