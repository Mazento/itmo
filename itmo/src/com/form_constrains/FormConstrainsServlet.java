package com.form_constrains;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")

public class FormConstrainsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String gender = req.getParameter("gender");
		String result = "";

		try {
			checkMail(email);
		} catch (InvalidEmailException e) {
			result += e.getMessage() + "<br>";
		}

		try {
			checkPassword(password);
		} catch (InvalidPasswordException e) {
			result += e.getMessage() + "<br>";
		}

		try {
			checkName(name);
		} catch (InvalidNameException e) {
			result += e.getMessage() + "<br>";
		}

		try {
			checkPhone(phone);
		} catch (InvalidPhoneException e) {
			result += e.getMessage() + "<br>";
		}

		try {
			checkGender(gender);
		} catch (InvalidGenderException e) {
			result += e.getMessage() + "<br>";
		}

		resp.getWriter().println(result);
	}	

	private static void checkMail(String email) throws InvalidEmailException {
		Boolean atsymb = false;
		Boolean dotsymb = false;
		if (email.length() == 0)
			throw new InvalidEmailException("Email field is empty");
		if (email.length() > 20)
			throw new InvalidEmailException("Email is too long. The limit is 20 symbols");
		for (char symbol:email.toCharArray()) {
			if (symbol == '@')
				atsymb = true;
			if (symbol == '.' && atsymb)
				dotsymb = true;
		}
		if (!dotsymb)
			throw new InvalidEmailException("Email is incorrect");
		return;
	}

	private static void checkPassword(String password) throws InvalidPasswordException {
		Boolean digit = false;
		Boolean capital = false;
		Boolean regular = false;
		if (password.length() == 0)
			throw new InvalidPasswordException("Password field is empty");
		for (char symbol:password.toCharArray()) {
			if (Character.isDigit(symbol))
				digit = true;
			if (Character.isUpperCase(symbol))
				capital = true;
			if (Character.isLowerCase(symbol))
				regular = true;
		}
		if (!(digit && capital && regular))
			throw new InvalidPasswordException("Your password doesn't contain at least one"
				+ " ordinal, one capital letter and one digit or shorter than 8 characters");
		return;
	}
	
	private static void checkName(String name) throws InvalidNameException {
		if (name.length() == 0)
			throw new InvalidNameException("Name field is empty");
		if (name.length() > 30)
			throw new InvalidNameException("Name is too long. The limit is 30 symbols");
		for (char symbol:name.toCharArray())
			if (!Character.isAlphabetic(symbol))
				throw new InvalidNameException("Name is incorrect");
		return;
	}
	
	private static void checkPhone(String phone) throws InvalidPhoneException {
		if (phone.length() == 0)
			throw new InvalidPhoneException("Phone field is empty");
		if (phone.length() > 15)
			throw new InvalidPhoneException("Phone number is too long. The limit is 15 symbols");
		for (char symbol:phone.toCharArray())
			if (!(Character.isDigit(symbol) || symbol == ' '))
				throw new InvalidPhoneException("Phone number is incorrect");
		return;
	}
	
	private static void checkGender(String gender) throws InvalidGenderException {
		if (!gender.equals("male") && !gender.equals("female"))
			throw new InvalidGenderException("List values violation");
		return;
	}
}
