package com.form_constrains;

public class InvalidEmailException extends Exception {
	public InvalidEmailException() {
		super();
	}
	public InvalidEmailException(String description) {
		super(description);
	}
}