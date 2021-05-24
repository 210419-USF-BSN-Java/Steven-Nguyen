package Service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import DAO.Employee_DAO_Defined;
import Model.ReimbRoles;
import Model.ReimbUsers;
import Utility.BusinessException;


@RunWith(MockitoJUnitRunner.class)
public class Employee_Service_DefinedTest {

	@Mock()
	Employee_DAO_Defined ed;
	
	@InjectMocks
	Employee_Service_Defined employeeService;
	private static EmployeeService es;
	private ReimbUsers users;
	
	@BeforeClass
	public static void setup() {
		es = new Employee_Service_Defined();
		
	}
	
	@Before
	public void Setup() throws Exception{
		ReimbRoles roles = new ReimbRoles(1,"Employee");
		users = new ReimbUsers(1,"stevien","1234","steven","nguyen","email",roles);	
		}
		
	@Test
	public void LoginChecktest() {
		String username ="stevien";
		String password = "1234";
		try {
			assertEquals("Username is invalid", es.loginCheck(username, password));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@Test
	public void viewPendingTest() {
		String username = "stevien";
		ed = new Employee_DAO_Defined();
		try {
			int id = ed.loginCheck(username).getId();
			assertEquals(false, es.viewPending(id));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void viewResolvedTest() {
		String username = "stevien";
		ed = new Employee_DAO_Defined();
		try {
			int id = ed.loginCheck(username).getId();
			assertEquals(false, es.viewResolved(id));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void submitTest() {
		String type = "food";
		double cost = 500;
		String desc = "it sucked";
		int id = 1;
		try {
			assertEquals(true, es.submitReimb(type, cost, desc, 1));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
