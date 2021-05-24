package DAO;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import Model.ReimbRoles;
import Model.ReimbUsers;
import Utility.BusinessException;

@RunWith(MockitoJUnitRunner.class)
public class Employee_DAO_DefinedTest {
	
	@Mock()
	Employee_DAO_Defined ed;
	
	private ReimbUsers users;
	
	@Before
	public void setup() throws Exception{
	ReimbRoles roles = new ReimbRoles(1,"Employee");
	users = new ReimbUsers(1,"stevien","1234","steven","nguyen","email",roles);	
	}
	
	@Test
	public void isValidLoginTest() {
		ed = new Employee_DAO_Defined();
		String username = "stevien";
		try {
			assertEquals(users, ed.loginCheck(username));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void viewPendingTest() {
		ed = new Employee_DAO_Defined();
		String username = "stevien";
		
		try {
			int id = ed.loginCheck(username).getId();
			assertEquals(false, ed.viewPending(id));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void viewResolvedTest() {
		ed = new Employee_DAO_Defined();
		String username = "stevien";
		
		try {
			int id = ed.loginCheck(username).getId();
			assertEquals(false, ed.viewResolved(id));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
