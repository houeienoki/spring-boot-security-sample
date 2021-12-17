package com.example.jwt;

import java.util.Date;

import com.example.domain.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtToken {
	
	private static final long TOKEN_VAILD_DURATION = 1000L * 60L * 60L;
	private static final String TOKEN_SECRET_KEY = "This is secrect!";
	
	private String value;
	
	public JwtToken(String value) {
		this.value = value;
	}
	
	public JwtToken(User user) {
		Date iat = new Date();
		Date exp = new Date(iat.getTime() + TOKEN_VAILD_DURATION);
		this.value = Jwts.builder()
							.setSubject(user.getUsername())
							.setIssuedAt(iat)
							.setExpiration(exp)
							.signWith(SignatureAlgorithm.HS256, TOKEN_SECRET_KEY)
							.compact();
	}
	
	@Override
	public String toString() {
		return this.value;
	}
	
	public boolean isValid() {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(TOKEN_SECRET_KEY).parseClaimsJws(this.value);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getUserName() {
		return Jwts.parser().setSigningKey(TOKEN_SECRET_KEY).parseClaimsJws(this.value).getBody().getSubject();
	}
}
