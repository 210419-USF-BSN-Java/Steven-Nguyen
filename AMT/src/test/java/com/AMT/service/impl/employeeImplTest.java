package com.AMT.service.impl;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Item;
import com.AMT.service.employeeService;

public class employeeImplTest {
	
private static employeeService es;

	@BeforeClass
	public static void setup() {
	es = new employeeImpl();
	}
	
	@Test
	public void isvalidlogin() {
		String username = "emplo1";
		String pw = "password1";
		try {
			assertEquals(true, es.logincheck(username,pw));
		} catch (accountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void isRemoveItemByNameValid() {
		String item = "#$@@#";
		try {
			assertEquals(false,es.removeItemByName(item));
		} catch (itemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void isRemoveItemNameExist() {
		String name = "Sasd tooth pick";
		try {
			assertEquals(false,es.removeItemByName(name));
		} catch (itemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void isretrieveOfferByIdValid() {
		int x = 54;
		try {
			assertEquals("fsdfs", es.retrieveOfferById(x));
		} catch (businessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void isAcceptOfferValid() {
		int x = 50;
		int y = 50;
		try {
			assertEquals("sdfsd",es.acceptOffer(x, y));
		} catch (businessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void isManagerCheckValid() {
		String username = "employee1";
		
		try {
			assertFalse(es.managerCheck(username));
		} catch (accountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void isManagerCheckValid2() {
		String username = "manager2";
		
		try {
			assertFalse(es.managerCheck(username));
		} catch (accountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void isFireValid() {
		int id = 2;
		try {
			assertEquals(1,es.fireEmployee(id));
		} catch (accountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
