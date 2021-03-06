package com.AMT.service;

import java.util.List;

import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Customer;
import com.AMT.model.Item;
import com.AMT.model.Payment;

public interface customerService {
	
public void createCustomer(Customer newCustomer) throws accountException;
public Boolean logincheck(String username, String password) throws businessException;
public List<Item> viewItems() throws itemException;
public void addOffer(double cOffer, int id, int merchid) throws businessException;
public Customer getId(String username) throws businessException;
public void fullPayment(int customerid) throws businessException;
public double retrieveCost(int customerid) throws businessException;
public void addWeeklyPayment(int customerid, double weekly) throws businessException;
public Payment paymentInformation(Customer id) throws businessException;
public List<String> viewOwnedItems(int id) throws businessException;

}
