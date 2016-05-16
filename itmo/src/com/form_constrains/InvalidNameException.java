package com.form_constrains;

public class InvalidNameException extends Exception {
	public InvalidNameException() {
		super();
	}
	public InvalidNameException(String description) {
		super(description);
	}
}