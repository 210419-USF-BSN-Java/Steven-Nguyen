package com.AMT.dao;

import java.util.List;

import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Employee;
import com.AMT.model.Item;
import com.AMT.model.Offer;
import com.AMT.model.Payment;

public interface employeeDAO extends GenericDao<Employee>{
public String retrieveDbUsername(String username) throws businessException;
public String retrieveDbPassword(String password) throws businessException;
public void addItem(Item newItem) throws itemException;
public int deleteItemByName(String itemName) throws itemException;
public Employee logincheck(String username) throws accountException;
public List<Offer> viewOffers() throws itemException;
public int acceptOffer(int merchId, int offerId) throws businessException;
public Offer retrieveOfferById(int oid) throws businessException;
public void updateMerchStatus(int merchId) throws businessException;
public void rejectOffer(int oid) throws businessException;
public void registerPayment(Payment bill) throws businessException;
public List<Payment> allPayments() throws businessException;
public List<Employee> listEmployee() throws businessException;
public Employee employeeManager(String username) throws accountException;
public int fireEmployee(int id) throws accountException;
public int createEmployee(Employee newEmployee) throws accountException;
}
