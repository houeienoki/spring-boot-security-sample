package com.example.domain.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.User;
import com.example.domain.user.service.UserService;
import com.example.jwt.JwtToken;
import com.example.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> findByUsername(String username) {
		return this.userRepository.findById(username);
	}

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		String credentials = token.getCredentials().toString();
		if (credentials == null || credentials.isEmpty()) {
			throw new UsernameNotFoundException("x-auth-token header must not be empty.");
		}
		
		JwtToken jwtToken = new JwtToken(credentials);
		if (!jwtToken.isValid()) {
			throw new UsernameNotFoundException("Token is not valid.");
		}
		
		String username = jwtToken.getUserName();
		User user = this.userRepository.findById(username).orElse(null);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole()));
		return (UserDetails) new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
