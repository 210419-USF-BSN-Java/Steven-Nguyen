package com.AMT.model;

public class Customer {
	private String firstName;
	private String lastName;
	private String userName;
	private String passW;
	private int customerid;
	
	public Customer() {
		
	}
	public Customer(String firstName, String lastName, String userName, String passW) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.passW = passW;
	}
	public Customer(String firstName, String lastName, String userName, String passW, int customerid) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.passW = passW;
		this.customerid = customerid;
	}
	
	
	public Customer(String userName) {
		super();
		this.userName = userName;
	}
	public Customer(int customerid) {
		super();
		this.customerid = customerid;
	}
	
	public Customer(String firstName, String lastName, int customerid) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerid = customerid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassW() {
		return passW;
	}
	public void setPassW(String passW) {
		this.passW = passW;
	}
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	@Override
	public String toString() {
		return "[customerid=" + customerid + "]";
	}
	

	
}
