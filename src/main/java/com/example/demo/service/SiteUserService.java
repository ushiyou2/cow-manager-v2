package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SiteUserService {

	private final SiteUserRepository repository;
	
	public List<SiteUser> findAll(){
		return repository.findAll();
	}
	
	public Optional<SiteUser> findByEmail(String email) {
        return repository.findByEmail(email);
    }
	
	public Optional<SiteUser> findByUsername(String username){
		return repository.findByUsername(username);
	}

    public SiteUser save(SiteUser siteUser) {
        return repository.save(siteUser);
    }
    
    public Optional<SiteUser> findUserFromOAuth2User(OAuth2User oauth2User) {
        if (oauth2User == null) {
            return Optional.empty();
        }

        String email = oauth2User.getAttribute("email");
        return repository.findByEmail(email);
    }
    
    public SiteUser findUserFromOAuth2(OAuth2User oauth2User) {
        if (oauth2User == null) {
            throw new NoSuchElementException("OAuth2User not found");
        }

        String email = oauth2User.getAttribute("email");
        Optional<SiteUser> optionalUser = repository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NoSuchElementException("SiteUser not found for email: " + email);
        }
    }
	
}
