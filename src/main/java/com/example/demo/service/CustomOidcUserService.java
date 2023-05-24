package com.example.demo.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.example.demo.model.SiteUser;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.SiteUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOidcUserService extends OidcUserService {

    private final SiteUserRepository userRepository; 

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);
        
        try {
             return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

     private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        UserInfo googleUserInfo = new UserInfo(oidcUser.getAttributes());

        // ユーザーをDBに保存

        Optional<SiteUser> userOptional = userRepository.findByEmail(googleUserInfo.getEmail());
        if (!userOptional.isPresent()) {
            SiteUser user = new SiteUser();
            user.setEmail(googleUserInfo.getEmail());
            user.setUsername(googleUserInfo.getName());
            user.setRegistrationDate(LocalDate.now());

            userRepository.save(user);
        }   

        return oidcUser;
    }
}
