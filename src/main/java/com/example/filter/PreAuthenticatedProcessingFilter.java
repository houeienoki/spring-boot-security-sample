package com.example.filter;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter  {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return "";
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader("x-auth-token")).orElse(null);
	}

}
