package com.example.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.model.User;
import com.example.domain.user.service.UserService;
import com.example.jwt.JwtToken;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping
	@CrossOrigin(origins = { "http://localhost:3000" }, exposedHeaders = { "x-auth-token" })
	public void post(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse httpServletResponse) {
		User user = this.userService.findByUsername(username).orElse(null);
		System.out.println(passwordEncoder.encode(password));
		if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
			httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		}
		
		httpServletResponse.setHeader("x-auth-token", new JwtToken(user).toString());
		httpServletResponse.setStatus(HttpStatus.OK.value());
	}

}
