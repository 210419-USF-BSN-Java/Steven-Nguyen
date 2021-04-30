package com.AMT.dao;

import java.util.List;

import com.AMT.exception.accountException;
import com.AMT.exception.businessException;
import com.AMT.exception.itemException;
import com.AMT.model.Employee;
import com.AMT.model.Item;
import com.AMT.model.Offer;

public interface employeeDAO extends GenericDao<Employee>{
public String retrieveDbUsername(String username) throws businessException;
public String retrieveDbPassword(String password) throws businessException;
public void addItem(Item newItem) throws itemException;
public String deleteItemByName(String itemName) throws itemException;
public Employee logincheck(String username) throws accountException;
public List<Offer> viewOffers() throws itemException;


}
