package com.todolist.todolist.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

	private String SECRET_KEY = "Secret";

	public String generateToken(String username, String name) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("name", name);
		return createToken(username, claims);
	}

	private String createToken(String username, Map<String, Object> claims) {
		return Jwts.builder().addClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 10000 * 60))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public boolean isTokenValid(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (SignatureException ex) {
			System.out.println("Invalid JWT Signature");
		} catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT Token");
		} catch (ExpiredJwtException ex) {
			System.out.println("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty");
		}
		return false;

	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

}
