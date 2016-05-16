package com.form_constrains;

public class InvalidPhoneException extends Exception {
	public InvalidPhoneException() {
		super();
	}
	public InvalidPhoneException(String description) {
		super(description);
	}
}