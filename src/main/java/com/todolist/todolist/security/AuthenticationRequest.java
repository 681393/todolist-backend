package com.todolist.todolist.security;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid Email")
	private String email;

	@NotBlank(message = "Password cannot be blank")
	private String password;
}
