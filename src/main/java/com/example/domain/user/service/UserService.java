package com.example.domain.user.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.example.domain.user.model.User;

public interface UserService extends AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
	public Optional<User> findByUsername(String username);
}
