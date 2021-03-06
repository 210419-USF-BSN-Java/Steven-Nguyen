package com.AMT.service;

import java.util.List;

import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Employee;
import com.AMT.model.Item;
import com.AMT.model.Offer;
import com.AMT.model.Payment;

public interface employeeService {
	public boolean logincheck(String username, String password) throws accountException;
	public void addItem(Item newItem) throws itemException;
	public int removeItemByName(String itemName) throws itemException;
	public List<Offer> viewOffers() throws itemException;
	public int acceptOffer(int merchId, int offerId) throws businessException;
	public void updateMerchStatus(int merchId) throws businessException;
	// do 1 2 and 3
	public Offer retrieveOfferById(int oid) throws businessException;
	public void rejectOffer(int oid) throws businessException;
	public List<Employee> listEmployee() throws businessException;
	public void registerPayment(Payment bill) throws businessException;
	public List<Payment> allPayments() throws businessException;
	public boolean managerCheck(String username) throws accountException;
	public int fireEmployee(int id) throws accountException;
	public int createEmployee(Employee newEmployee) throws accountException;
}
