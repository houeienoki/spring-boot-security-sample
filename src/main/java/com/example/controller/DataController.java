package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {
	@PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
	@PostMapping("/user")
	@CrossOrigin(origins = { "http://localhost:3000" })
	public String postUser() {
		return "data";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/admin")
	@CrossOrigin(origins = { "http://localhost:3000" })
	public String postAdmin() {
		return "data";
	}
}
