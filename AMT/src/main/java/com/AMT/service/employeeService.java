package com.AMT.service;

import java.util.List;

import com.AMT.exception.accountException;
import com.AMT.exception.itemException;
import com.AMT.model.Item;
import com.AMT.model.Offer;

public interface employeeService {
	public boolean logincheck(String username, String password) throws accountException;
	public void addItem(Item newItem) throws itemException;
	public String removeItemByName(String itemName) throws itemException;
	public List<Offer> viewOffers() throws itemException;

}