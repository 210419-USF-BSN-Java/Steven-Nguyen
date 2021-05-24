package Service;

import java.util.List;

import Model.Reimb;
import Model.ReimbUsers;
import Utility.BusinessException;

public interface EmployeeService {
public Boolean loginCheck(String username, String password) throws BusinessException;
public List<Reimb> viewPending(int userId) throws BusinessException;
public List<Reimb> viewResolved(int userId) throws BusinessException;
public Boolean submitReimb(String type, double amount, String description, int userId) throws BusinessException;
public ReimbUsers getUser(String username) throws BusinessException;
}
