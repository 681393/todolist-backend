package com.todolist.todolist.security;

public class AuthenticationResponse {
	private final String jwt;

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = "Bearer " + jwt;
	}

	public String getJwt() {
		return jwt;
	}

}
