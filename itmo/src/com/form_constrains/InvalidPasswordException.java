package com.form_constrains;

public class InvalidPasswordException extends Exception {
	public InvalidPasswordException() {
		super();
	}
	public InvalidPasswordException(String description) {
		super(description);
	}
}