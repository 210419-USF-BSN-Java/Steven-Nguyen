package com.AMT.service.impl;

import java.util.List;

import com.AMT.dao.customerDAO;
import com.AMT.dao.impl.customerDAOImpl;
import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Customer;
import com.AMT.model.Item;
import com.AMT.model.Payment;
import com.AMT.service.customerService;
import com.AMT.util.SingleLogger;
import com.AMT.util.Validations;
import com.AMT.util.passwordHashing;

public class customerImpl implements customerService {

	customerDAO customerdao = new customerDAOImpl();

	public void createCustomer(Customer Customer) throws accountException {
		if (Validations.usernameValidations(Customer.getUserName()) == true
				&& Validations.passwordValidations(Customer.getPassW()) == true) {
			customerdao.createCustomer(Customer);
		}
		if (Validations.usernameValidations(Customer.getUserName()) == false) {

		}
		if (Validations.passwordValidations(Customer.getPassW()) == false) {
		} 
		

	}

	@Override
	public Boolean logincheck(String username, String password) throws businessException {

		if (customerdao.logincheck(username).getUserName() == null) {
			throw new businessException("Username or password enter is invalid");

		}if(!customerdao.logincheck(username).getPassW().equals(password)
				&& !customerdao.logincheck(username).getPassW().equals(passwordHashing.doHashing(password))) {
			throw new businessException("Password is incorrect");
			
		}

		if (customerdao.logincheck(username).getUserName().equals(username)
				&& customerdao.logincheck(username).getPassW().equals(password)) {
			SingleLogger.getLogger().info("login successful");

			return true;
		}
		if (customerdao.logincheck(username).getUserName().equals(username)
				&& customerdao.logincheck(username).getPassW().equals(passwordHashing.doHashing(password))) {
			return true;
		}
		return false;
	}

	@Override
	public List<Item> viewItems() throws itemException {
		if (customerdao.viewItems() == null || customerdao.viewItems().size() < 0) {
			throw new itemException("No items currently on sale");
		}

		return customerdao.viewItems();
	}

	@Override
	public Customer getId(String username) throws businessException {
		Customer id = customerdao.logincheck(username);
		return id;
	}

	@Override
	public void addOffer(double cOffer, int id, int merchid) throws businessException {
		
		if(customerdao.retrieveItemInformation(merchid).getItemId() == 0) {
			throw new businessException("Invalid item id");
		}
		
		if (Validations.nonNegativeNumbers(cOffer)) {
		}

		customerdao.addOffer(cOffer, id, merchid);

	}

	@Override
	public void fullPayment(int customerid) throws businessException {
		
		customerdao.fullPayment(customerid);

	}

	@Override
	public double retrieveCost(int customerid) throws businessException {

		return customerdao.retrieveCost(customerid);
	}

	@Override
	public void addWeeklyPayment(int customerid, double weekly) throws businessException {
		if (Validations.nonNegativeNumbers(weekly)) {
			SingleLogger.getLogger().warn("Somehow addweeklypayment failed");
		}
		customerdao.addWeeklyPayment(customerid, weekly);

	}

	@Override
	public Payment paymentInformation(Customer id) throws businessException {
		Payment paymentInformation = new Payment();
		paymentInformation = customerdao.retrievePaymentInformation(id);
		return paymentInformation;
	}

	@Override
	public List<String> viewOwnedItems(int id) throws businessException {
		
		return customerdao.viewOwnedItems(id);
	}

}
