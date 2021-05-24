package Service;

import java.util.List;

import DAO.Employee_DAO_Defined;
import DAO.EmployeesDAO;
import Model.Reimb;
import Model.ReimbUsers;
import Utility.BusinessException;

public class Employee_Service_Defined implements EmployeeService {
	EmployeesDAO ed = new Employee_DAO_Defined();

	@Override
	public Boolean loginCheck(String username, String password) throws BusinessException {
		if (ed.loginCheck(username).getUname() == null) {
			return false;
		}
		if (!ed.loginCheck(username).getPword().equals(password)) {
			return false;
		}
		if(ed.loginCheck(username).getUname().equals(username) && ed.loginCheck(username).getPword().equals(password)) {
			return true;
		}
		return false;
	}
	
	@Override
	public ReimbUsers getUser(String username) throws BusinessException {
		ReimbUsers user = ed.loginCheck(username);
		return user;
	}
	
	@Override
	public List<Reimb> viewPending(int userId) throws BusinessException {
		
		return ed.viewPending(userId);
	}

	@Override
	public List<Reimb> viewResolved(int userId) throws BusinessException {
		// TODO Auto-generated method stub
		return ed.viewResolved(userId);
	}

	@Override
	public Boolean submitReimb(String type, double amount, String description, int userId) throws BusinessException {
		if(type.equals("") || type.equals(null) || description.equals("") || description.equals(null)) {
			return false;
		}
		if(amount <= 0) {
			return false;
		}
		if(ed.submitReimb(type, amount, description, userId) == false){
			return true;
		}else {
			return false;
		}
		
	}

	
	
}
