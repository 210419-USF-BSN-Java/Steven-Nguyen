package com.AMT.service.impl;

import java.util.List;

import com.AMT.dao.customerDAO;
import com.AMT.dao.impl.customerDAOImpl;
import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Customer;
import com.AMT.model.Item;
import com.AMT.service.customerService;

public class customerImpl implements customerService {

	customerDAO customerdao = new customerDAOImpl();

	public void createCustomer(Customer Customer) throws accountException {
		customerdao.createCustomer(Customer);

	}

	@Override
	public Boolean logincheck(String username, String password) throws businessException {
		Customer c = new Customer();
		if (customerdao.logincheck(username).getUserName() == null) {

			return false;
		}
		if (customerdao.logincheck(username).getUserName().equals(username)
				&& customerdao.logincheck(username).getPassW().equals(password)) {
		

			return true;
		}
		return false;
	}

	

	@Override
	public List<Item> viewItems() throws itemException {

		return customerdao.viewItems();
	}


	@Override
	public Customer getId(String username) throws businessException {
		int id = customerdao.logincheck(username).getCustomerid();
		Customer c = new Customer(id);
		return c;
	}

	@Override
	public void addOffer(double cOffer, int id, int merchid) throws businessException {
		customerdao.addOffer(cOffer, id, merchid);
		
	}

}