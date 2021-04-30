package com.AMT.service.impl;

import java.util.List;

import com.AMT.dao.employeeDAO;
import com.AMT.dao.impl.employeeDAOimpl;
import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Item;
import com.AMT.model.Offer;
import com.AMT.service.employeeService;

public class employeeImpl implements employeeService {
	employeeDAO ed = new employeeDAOimpl();
	

	@Override
	//Here we are comparing userinput to database data
	public boolean logincheck(String username, String password) throws accountException {
		if(ed.logincheck(username).getEmpUsername() == null) {
			throw new accountException("Account does not exist");
		}if(ed.logincheck(username).getEmpUsername().equals(username) && 
				ed.logincheck(username).getEmpPw().equals(password)) {
			return true; 
		}
		return false;
	}

	@Override
	public void addItem(Item newItem) throws itemException {
		ed.addItem(newItem);
	}

	@Override
	public String removeItemByName(String itemName) throws itemException {
		return ed.deleteItemByName(itemName);
	}

	@Override
	public List<Offer> viewOffers() throws itemException {
		// TODO Auto-generated method stub
		return ed.viewOffers();
	}


}
