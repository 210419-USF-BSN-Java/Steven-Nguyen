package com.AMT.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.AMT.exception.accountException;
import com.AMT.exception.businessException;

public class ValidationsTest {

	@Test
	public void test() {
		//username validation test
		String username = "";

		try {
			assertEquals("Username input cannot be empty",Validations.usernameValidations(username));
		} catch (accountException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	
	}
	@Test public void isPasswordValid() {
		String password ="df2gfhfhfhgfnfhgfhfhfhhgfhfgfv";
		try {
			assertEquals("Password is too long or too short", Validations.passwordValidations(password));
		}catch(accountException e) {
			e.getMessage();
		}
	}
	@Test 
	public void isAmountLessThanZero() {
		double amount = 0;
		try {
		assertTrue("The number inputted is 0 or less than zero", Validations.nonNegativeNumbers(amount));
		}catch(businessException e) {
			e.getMessage();
		}
	}
	@Test
	public void yesNo() {
		String answer = "no";
		String answer1 ="No";
		String answer2 ="NO";
		String answer3 ="n";
		
		assertEquals(false,Validations.approve(answer));
		assertEquals(false,Validations.approve(answer1));
		assertEquals(false,Validations.approve(answer2));
		assertEquals(false,Validations.approve(answer3));
		
	}

}
