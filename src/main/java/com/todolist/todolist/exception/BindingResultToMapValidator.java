package com.todolist.todolist.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class BindingResultToMapValidator {

	public Map<String, String> validate(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		if (result.hasErrors()) {
			errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		}
		return errors;
	}
}
