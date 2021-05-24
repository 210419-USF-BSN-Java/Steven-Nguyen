package DAO;

import java.util.List;

import Model.Reimb;
import Model.ReimbUsers;
import Utility.BusinessException;

public interface EmployeesDAO {

	public ReimbUsers loginCheck(String username) throws BusinessException; // can use this to view customer information
	public Boolean submitReimb(String type, double amount, String description, int userId) throws BusinessException; // adding in reimburse return 1 or 0 as pass/fail
	public List<Reimb> viewPending(int userId) throws BusinessException;// return reimburse object then get status object
	public List<Reimb> viewResolved(int userId) throws BusinessException;// return reimburse object then get status object
	public ReimbUsers updateUser(String username) throws BusinessException;
}