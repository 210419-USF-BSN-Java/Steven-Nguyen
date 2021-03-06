package com.AMT.util;

import com.AMT.exception.accountException;
import com.AMT.exception.businessException;

public class Validations {

	public static boolean usernameValidations(String username) throws accountException{
		if(username == null) {
			throw new accountException("Username input cannot be empty");
		}
		if(!username.matches("[a-zA-Z0-9]{3,15}")) {
			throw new accountException("Username size has to be bigger than 3 and smaller than 15");
		}if(username.matches("[0-9]{0,100}")) {
			throw new accountException("username cannot contain only numbers");
		}

		return true;
	}

	public static boolean passwordValidations(String password) throws accountException{
		if(password == null) {
			throw new accountException("Password can't be empty");
		}if(!password.matches("[a-zA-Z0-9]{8,60}")) {
			throw new accountException("Password is too long or too short");
		}

		return true;
	}

	public static boolean nonNegativeNumbers(double number) throws businessException{
		if(number <= 0) {
			throw new businessException("The number inputted is 0 or less than zero");
		}
		return true;
		

	}

	public static boolean approve(String answer) {
		if (answer.matches("[yY]") || answer.equals("Yes") || answer.equals("yes") || answer.equals("YES")) {
			return true;
		}
		if (answer.matches("[nN]") || answer.equals("No") || answer.equals("no") || answer.equals("NO")) {
			return false;
		} else {
			return false;
		}
	}

}
