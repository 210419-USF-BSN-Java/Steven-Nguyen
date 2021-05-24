package DAO;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import Model.ReimbRoles;
import Model.ReimbUsers;
import Utility.BusinessException;

public class Manager_DAO_DefinedTest {

	@Mock()
	Manager_DAO_Defined md;
	private ReimbUsers users;

	@Before
	public void setup() throws Exception {
		ReimbRoles roles = new ReimbRoles(3, "Manager");
		users = new ReimbUsers(3, "manager1", "managerpw1", "Liquid", "Snake", "kill.snake.com", roles);
	}

	@Test
	public void loginChecktest() {
		md = new Manager_DAO_Defined();
		String username = "manager1";
		try {
			assertEquals(users, md.loginCheck(username));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getReimbursementListTest() {
		md = new Manager_DAO_Defined();

		try {
			assertEquals(false, md.getReimbursementList());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getResolvedListTest() {
		md = new Manager_DAO_Defined();

		try {
			assertEquals(false, md.viewAllResolved());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getAllEmployeeTest() {
		md = new Manager_DAO_Defined();

		try {
			assertEquals(false, md.viewAllEmployees());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getAllEmployeeByIdTest() {
		md = new Manager_DAO_Defined();
		int id = 4;
		try {
			assertEquals(false, md.viewReimbByEmployeeId(id));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void decideTest() {
		md = new Manager_DAO_Defined();
		int userid = 2;
		int reimbid = 1;
		try {
			assertEquals(true, md.decide(reimbid, userid));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
