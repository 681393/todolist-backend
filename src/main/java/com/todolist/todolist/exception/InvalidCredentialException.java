package com.todolist.todolist.exception;

public class InvalidCredentialException {

	private String _error;

	public InvalidCredentialException() {
		this._error = "Invalid Credentials";
	}

	public String get_error() {
		return _error;
	}

	public void set_error(String _error) {
		this._error = _error;
	}
}
