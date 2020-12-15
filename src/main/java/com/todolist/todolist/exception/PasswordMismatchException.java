package com.todolist.todolist.exception;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.todolist.todolist.persistent.UserData;

@Component
public class PasswordMismatchException implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserData.class.isInstance(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserData userData = (UserData) target;
		if (!userData.getPassword().equals(userData.getConfirmPassword())) {
			errors.rejectValue("password", "match", "Password and Confirm Password should be same");
		}

	}

}
