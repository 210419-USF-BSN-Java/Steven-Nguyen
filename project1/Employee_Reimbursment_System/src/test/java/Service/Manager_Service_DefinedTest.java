package Service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import DAO.Manager_DAO_Defined;
import Model.ReimbRoles;
import Model.ReimbUsers;
import Utility.BusinessException;

public class Manager_Service_DefinedTest {
	
	@Mock()
	private static ManagerService ms;
	private ReimbUsers users;

	@BeforeClass
	public static void Setup() {
		ms = new Manager_Service_Defined();
	}
	
	@Before
	public void setup() throws Exception {
		ReimbRoles roles = new ReimbRoles(3, "manager");
		users = new ReimbUsers(3, "manager1", "managerpw1", "Liquid", "Snake", "kill.snake.com", roles);
	}

	@Test
	public void loginChecktest() {
		String username ="manager2";
		String password = "manager2pw";
		try {
			assertEquals(users, ms.loginCheck(username, password));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@Test
	public void getReimbursementListTest() {
		try {
			assertEquals(false, ms.getReimbursementList());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getResolvedListTest() {

		try {
			assertEquals(false, ms.viewAllResolved());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getEmployeeListTest() {

		try {
			assertEquals(false, ms.viewAllEmployees());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getEmployeeByIdTest() {
		int id = 0;
		try {
			assertEquals(false, ms.viewReimbByEmployeeId(id));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void decideServiceTest() {
		int userid = 2;
		int reimbid = 50;
		try {
			assertEquals(false, ms.decide(reimbid, userid));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
