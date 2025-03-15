package com.challenge.brokagefirmapp.service;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationAuthenticationProvider implements AuthenticationProvider {

    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        if ("adminUser".equals(username) && "adminPassword".equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, List.of());
        } else {
            throw new AuthenticationCredentialsNotFoundException("Error!");
        }
    }
}
