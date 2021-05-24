package Service;

import java.util.List;

import org.apache.log4j.Logger;

import DAO.ManagerDAO;
import DAO.Manager_DAO_Defined;
import Model.Reimb;
import Model.ReimbUsers;
import Utility.BusinessException;

public class Manager_Service_Defined implements ManagerService{
	ManagerDAO md = new Manager_DAO_Defined();
	Logger log = Logger.getLogger(Manager_Service_Defined.class);
	@Override
	public ReimbUsers loginCheck(String username, String password) throws BusinessException {
		if (md.loginCheck(username).getUname() == null) {
			log.info("User inserted invalid username");
			throw new BusinessException("Username is invalid");
		}
		if (!md.loginCheck(username).getPword().equals(password)) {
			log.info("User inserted invalid password");
			throw new BusinessException("Pasword is invalid");
		}
		return md.loginCheck(username);
	}

	@Override
	public List<Reimb> getReimbursementList() throws BusinessException {
		return md.getReimbursementList();
	}

	@Override
	public Boolean decide(int reimbId, int userId) throws BusinessException {
		if(md.decide(reimbId, userId) == false ) {
			return false;
		}
		return true;
	}

	@Override
	public List<Reimb> viewAllResolved() throws BusinessException {
		// TODO Auto-generated method stub
		return md.viewAllResolved();
	}

	@Override
	public List<ReimbUsers> viewAllEmployees() throws BusinessException {
		// TODO Auto-generated method stub
		return md.viewAllEmployees();
	}
	
	@Override
	public List<Reimb> viewReimbByEmployeeId(int id) throws BusinessException {
		if(id <=0) {
			log.info("Id can't be found in service method getEmployee by Id");
			throw new BusinessException("id can not be found");
		}
		return md.viewReimbByEmployeeId(id);
	}
}