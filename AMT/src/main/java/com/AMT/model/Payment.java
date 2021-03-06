package com.AMT.model;

public class Payment {
private int paymentID;
private Customer customer;
private Item item;
private Offer offer;
private double remaning;
private double weekly;
private double total;





public Payment(Customer customer, Item item, Offer offer, double remaning) {
	super();
	this.customer = customer;
	this.item = item;
	this.offer = offer;
	this.remaning = remaning;
	
}

public Payment() {
	super();
}


public Payment(Item item) {
	super();
	this.item = item;
}

public Payment(int paymentID, Customer customer, Item item, Offer offer, double remaning, double weekly, double total) {
	super();
	this.paymentID = paymentID;
	this.customer = customer;
	this.item = item;
	this.offer = offer;
	this.remaning = remaning;
	this.weekly = weekly;
	this.total = total;
}



public int getPaymentID() {
	return paymentID;
}
public void setPaymentID(int paymentID) {
	this.paymentID = paymentID;
}
public Customer getCustomer() {
	return customer;
}
public void setCustomer(Customer customer) {
	this.customer = customer;
}
public Item getItem() {
	return item;
}
public void setItem(Item item) {
	this.item = item;
}
public Offer getOffer() {
	return offer;
}
public void setOffer(Offer offer) {
	this.offer = offer;
}
public double getWeekly() {
	return weekly;
}
public void setWeekly(double weekly) {
	this.weekly = weekly;
}
public double getTotal() {
	return total;
}
public void setTotal(double total) {
	this.total = total;
}


public double getRemaning() {
	return remaning;
}

public void setRemaning(double remaning) {
	this.remaning = remaning;
}

@Override
public String toString() {
	return "Payment [paymentID=" + paymentID + ", customer=" + customer + ", item=" + item + ", offer=" + offer
			+ ", remaning=" + remaning + ", weekly=" + weekly + ", total=" + total + "]";
}



}
