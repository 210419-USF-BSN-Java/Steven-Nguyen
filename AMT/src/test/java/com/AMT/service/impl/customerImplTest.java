package com.AMT.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.AMT.dao.impl.customerDAOImpl;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Customer;

import com.AMT.service.customerService;
import com.AMT.util.SingleLogger;
import com.AMT.util.passwordHashing;


@RunWith(MockitoJUnitRunner.class) //junit will run alongside mockito
public class customerImplTest {

	//mock - create empty class to have your method pull data from a fake database, expected data
	@Mock()
	customerDAOImpl cd;
	
	@InjectMocks
	customerImpl cservice;
	
	private static customerService cs;
	private Customer customer;
	//variable container
	
	@BeforeClass
	public static void setup() {
		cs = new customerImpl();
	}

	@Before
	//Set up expected data from the database
	public void SetUp() throws Exception{
	customer = new Customer("Matthew","ChingCong","MatthewC","MatthewCPW",23); // return type from our dao class
	}// calling from the dao but not really just pretending LIKE MOCKING AHHH

	@Test
	public void isvalidloginTest() {
		String username = "harryn";
		String password = passwordHashing.doHashing("harryn");
		try {
			assertEquals(true, cs.logincheck(username, password));
		} catch (businessException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			SingleLogger.getLogger().info(e.getMessage());
		}
	}

	@Test
	public void isvalidlistitemTest() {
		try {
			assertEquals("No items currently on sale", cs.viewItems());
		} catch (itemException e) {
			SingleLogger.getLogger().info(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void isgetidvalidTest() {
		String username ="MatthewC";
		try {
			when(cd.logincheck(username)).thenReturn(customer);
			//when we call particular dao call in our service, automatically return the variable customer
		} catch (businessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(23,cservice.getId(username).getCustomerid());
			//this is what we are testing
		} catch (businessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
