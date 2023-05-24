package com.example.demo.messagingstompwebsocket;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
@ComponentScan("com.example.demo")
public class MessagingStompWebsocketApplication implements ApplicationRunner{
	
	private final ApplicationContext appContext;
	
	  public static void main(String[] args) {
		    SpringApplication.run(MessagingStompWebsocketApplication.class, args);
	  }
	  
	  @Override
	  public void run(ApplicationArguments args) throws Exception {
		  var allBeanNames = appContext.getBeanDefinitionNames();
		  for (var beanName: allBeanNames) {
			  log.info("Bean名： {}", beanName);
		  }
	  }
}
