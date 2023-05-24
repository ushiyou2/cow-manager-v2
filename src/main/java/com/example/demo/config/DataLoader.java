package com.example.demo.config;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.model.Cow;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.CowRepository;
import com.example.demo.repository.SiteUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

	private final CowRepository cowRepository;
	
	private final SiteUserRepository userRepository;
	
	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception{
		

	    var user_1 = new SiteUser();
	    user_1.setUsername("demo-user");
	    user_1.setEmail("demo@xyz.rrr");
	    user_1.setRegistrationDate(LocalDate.now());

	    userRepository.save(user_1);
	    

	    var cow_1 = new Cow();
	    cow_1.setCowNumber("1122");
	    cow_1.setBirthDate(LocalDate.now());
	    cow_1.setHealthStatus("Ok");
	    // Set the SiteUser for the Cow
	    Optional<SiteUser> siteUserOptional_1 = userRepository.findById(1L);
	    siteUserOptional_1.ifPresent(cow_1::setSiteUser);

	    var cow_2 = new Cow();
	    cow_2.setCowNumber("4444");
	    cow_2.setBirthDate(LocalDate.now());
	    cow_2.setHealthStatus("Not ok");
	     // Set the SiteUser for the Cow
	    Optional<SiteUser> siteUserOptional_2 = userRepository.findById(1L);
	    siteUserOptional_2.ifPresent(cow_2::setSiteUser);
	    
	    cowRepository.save(cow_1);
	    cowRepository.save(cow_2);

	}
}
