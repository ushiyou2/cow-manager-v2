package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.CredentialService;
import com.example.demo.service.CustomOidcUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final CredentialService service;
	
	private final CustomOidcUserService customOidcUserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(auth -> auth
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()
			)
			.oauth2Login(login -> login
				.loginPage("/login").permitAll()
				.userInfoEndpoint().oidcUserService(customOidcUserService)
			)
			.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
			);
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
		return http.build();
		
	}
	
	@Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration registration = CommonOAuth2Provider.GOOGLE
        		.getBuilder("google")
                .clientId(service.getClientId())
                .clientSecret(service.getClientSecret())
                .redirectUri("http://localhost:8080/login/oauth2/code/google")
                .scope("openid", "profile", "email")
                .build();
        return new InMemoryClientRegistrationRepository(registration);
    }
}
