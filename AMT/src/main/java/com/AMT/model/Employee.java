package com.AMT.model;

public class Employee {
	private int empId;
	private String empUsername;
	private String empPw;
	private Boolean manager;
	private String empName;
	
	public Employee() {
		super();
	}

	public Employee(String empUsername, String empPw) {
		super();
		this.empUsername = empUsername;
		this.empPw = empPw;
	}

	public Employee(String empUsername, String empPw, Boolean manager) {
		super();
		this.empUsername = empUsername;
		this.empPw = empPw;
		this.manager = manager;
	}

	public Employee(String empUsername, String empPw, Boolean manager, String empName) {
		super();
		this.empUsername = empUsername;
		this.empPw = empPw;
		this.manager = manager;
		this.empName = empName;
	}

	public Employee(int empId, String empUsername, String empPw, Boolean manager, String empName) {
		super();
		this.empId = empId;
		this.empUsername = empUsername;
		this.empPw = empPw;
		this.manager = manager;
		this.empName = empName;
	}

	public Employee(String empUsername, String empPw, String empName) {
		super();
		this.empUsername = empUsername;
		this.empPw = empPw;
		this.empName = empName;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpUsername() {
		return empUsername;
	}

	public void setEmpUsername(String empUsername) {
		this.empUsername = empUsername;
	}

	public String getEmpPw() {
		return empPw;
	}

	public void setEmpPw(String empPw) {
		this.empPw = empPw;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empUsername=" + empUsername + ", empPw=" + empPw + ", manager=" + manager
				+ ", empName=" + empName + "]";
	}

	public Boolean getManager() {
		return manager;
	}

	public void setManager(Boolean manager) {
		this.manager = manager;
	}
	
	
}
