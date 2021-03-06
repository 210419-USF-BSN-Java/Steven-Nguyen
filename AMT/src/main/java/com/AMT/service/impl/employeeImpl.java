package com.AMT.service.impl;

import java.util.List;

import com.AMT.dao.employeeDAO;
import com.AMT.dao.impl.employeeDAOimpl;
import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Employee;
import com.AMT.model.Item;
import com.AMT.model.Offer;
import com.AMT.model.Payment;
import com.AMT.service.employeeService;
import com.AMT.util.SingleLogger;
import com.AMT.util.Validations;
import com.AMT.util.passwordHashing;

public class employeeImpl implements employeeService {
	employeeDAO ed = new employeeDAOimpl();

	@Override
	public boolean logincheck(String username, String password) throws accountException {
		if (ed.logincheck(username).getEmpUsername() == null) {
			throw new accountException("Username or password enter is invalid");

		}

		if (!ed.logincheck(username).getEmpPw().equals(password)
				&& !ed.logincheck(username).getEmpPw().equals(passwordHashing.doHashing(password))) {
			throw new accountException("Password is incorrect");

		}
		if (ed.logincheck(username).getEmpUsername().equals(username)
				&& ed.logincheck(username).getEmpPw().equals(password)) {
			SingleLogger.getLogger().info("login successful");

			return true;
		}
		if (ed.logincheck(username).getEmpUsername().equals(username)
				&& ed.logincheck(username).getEmpPw().equals(passwordHashing.doHashing(password))) {

			SingleLogger.getLogger().info("Password hashing matches success");
			return true;
		}

		throw new accountException("Unable to Login");

	}

	@Override
	public void addItem(Item newItem) throws itemException {
		double x = newItem.getItemPrice();
		try {

			if (Validations.nonNegativeNumbers(x) == false) {
				throw new itemException("price can not be lowered than 0");
			}
		} catch (businessException e) {
			SingleLogger.getLogger().info(e);
		}

		ed.addItem(newItem);

	}

	@Override
	public int removeItemByName(String itemName) throws itemException {
		if (ed.deleteItemByName(itemName) == 0) {
			throw new itemException("Item name does not exist");
		}

		return ed.deleteItemByName(itemName);
	}

	@Override
	public List<Offer> viewOffers() throws itemException {

		return ed.viewOffers();
	}

	@Override
	public Offer retrieveOfferById(int oid) throws businessException {
		if (oid <= 0) {
			throw new businessException("Offer id is invalid");
		}
		if (oid != ed.retrieveOfferById(oid).getOfferId()) {
			throw new businessException("Offer id does not exist");
		}
		return ed.retrieveOfferById(oid);
	}

	@Override
	public int acceptOffer(int merchId, int offerId) throws businessException {
		if (ed.acceptOffer(merchId, offerId) != 1) {
			throw new businessException("Merch Id or offer Id is incorrect, please try again");
		} else {
			return ed.acceptOffer(merchId, offerId);
		}
	}

	@Override
	public void updateMerchStatus(int merchId) throws businessException {
		ed.updateMerchStatus(merchId);

	}

	@Override
	public void rejectOffer(int oid) throws businessException {
		ed.rejectOffer(oid);

	}

	@Override
	public void registerPayment(Payment bill) throws businessException {
		ed.registerPayment(bill);
	}

	@Override
	public List<Payment> allPayments() throws businessException {
		// TODO Auto-generated method stub
		return ed.allPayments();
	}
//NO VOID METHODS NEXT TIME

	@Override
	public boolean managerCheck(String username) throws accountException {
		if (ed.employeeManager(username).getManager().equals(true)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Employee> listEmployee() throws businessException {

		return ed.listEmployee();
	}

	@Override
	public int fireEmployee(int id) throws accountException {
		int x = ed.fireEmployee(id);
		if(x == 0) {
			throw new accountException("Exiting to Employee Menu");
		}
		if (x <= 0) {
			throw new accountException("Employee id does not exist");
		}
		return 1;
	}

	@Override
	public int createEmployee(Employee newEmployee) throws accountException {
		if (Validations.usernameValidations(newEmployee.getEmpUsername()) == true
				&& Validations.passwordValidations(newEmployee.getEmpPw()) == true) {
			ed.createEmployee(newEmployee);
		}
		if (Validations.usernameValidations(newEmployee.getEmpUsername()) == false) {

		}
		if (Validations.passwordValidations(newEmployee.getEmpPw()) == false) {
		}

		return 1;
	}

}
