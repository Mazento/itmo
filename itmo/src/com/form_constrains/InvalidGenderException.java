package com.form_constrains;

public class InvalidGenderException extends Exception {
	public InvalidGenderException() {
		super();
	}
	public InvalidGenderException(String description) {
		super(description);
	}
}