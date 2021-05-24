package Service;

import java.util.List;

import Model.Reimb;
import Model.ReimbUsers;
import Utility.BusinessException;

public interface ManagerService {
	public ReimbUsers loginCheck(String username, String password) throws BusinessException; // can return null if user types does not match manager
	public List<Reimb> getReimbursementList() throws BusinessException;
	public Boolean decide(int reimbId, int userId) throws BusinessException;
	public List<Reimb> viewAllResolved() throws BusinessException;
	public List<ReimbUsers> viewAllEmployees() throws BusinessException;
	public List<Reimb> viewReimbByEmployeeId(int id) throws BusinessException;
}
