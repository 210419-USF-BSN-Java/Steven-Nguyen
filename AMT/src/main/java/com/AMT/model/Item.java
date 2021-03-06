package com.AMT.model;

public class Item {
	
private String itemName;
private double itemPrice;
private int itemId;
private String status;

public Item() {
	
}

public Item(String itemName, double itemPrice) {
	super();
	this.itemName = itemName;
	this.itemPrice = itemPrice;
}



public Item(int itemId) {
	super();
	this.itemId = itemId;
}


public Item(int itemId, String itemName, double itemPrice) {
	super();
	this.itemId = itemId;
	this.itemName = itemName;
	this.itemPrice = itemPrice;
}



public Item(String itemName, double itemPrice, int itemId, String status) {
	super();
	this.itemName = itemName;
	this.itemPrice = itemPrice;
	this.itemId = itemId;
	this.status = status;
}

public Item(String itemName, int itemId) {
	super();
	this.itemName = itemName;
	this.itemId = itemId;
}

public String getItemName() {
	return itemName;
}

public void setItemName(String itemName) {
	this.itemName = itemName;
}

public double getItemPrice() {
	return itemPrice;
}

public void setItemPrice(double itemPrice) {
	this.itemPrice = itemPrice;
}

public int getItemId() {
	return itemId;
}

public void setItemId(int itemId) {
	this.itemId = itemId;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}



@Override
public String toString() {
	return "[Id " + itemId + ": " + itemName + ", Starting Price:" + itemPrice + ", " + status +"]";
}



}