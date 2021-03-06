package com.AMT.model;

public class Offer {
	private int offerId;
	private Item item;
	private double customerOffer;
	private String accepted;
	private Customer customer;
	
	
	public Offer() {
		super();
	}


	public Offer(int offerId) {
		super();
		this.offerId = offerId;
	}


	public Offer(int offerId, Item item, double customerOffer, String accepted, Customer customer) {
		super();
		this.offerId = offerId;
		this.item = item;
		this.customerOffer = customerOffer;
		this.accepted = accepted;
		this.customer = customer;
	}


	public Offer(int offerId, Item item, double customerOffer) {
		super();
		this.offerId = offerId;
		this.item = item;
		this.customerOffer = customerOffer;
	}


	

	public Offer(Item item, double customerOffer, String accepted) {
		super();
		this.item = item;
		this.customerOffer = customerOffer;
		this.accepted = accepted;
	}


	public Offer(int offerId, Item item, double customerOffer, String accepted) {
		super();
		this.offerId = offerId;
		this.item = item;
		this.customerOffer = customerOffer;
		this.accepted = accepted;
	}


	public Offer(Item item, double customerOffer) {
		super();
		this.item = item;
		this.customerOffer = customerOffer;
	}


	public Offer(int offerId, Item item) {
		super();
		this.offerId = offerId;
		this.item = item;
	}


	public Offer(int offerId, Item item, double customerOffer, Customer customer) {
		super();
		this.offerId = offerId;
		this.item = item;
		this.customerOffer = customerOffer;
		this.customer = customer;
	}


	public int getOfferId() {
		return offerId;
	}


	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}


	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}


	public double getCustomerOffer() {
		return customerOffer;
	}


	public void setCustomerOffer(double customerOffer) {
		this.customerOffer = customerOffer;
	}


	public String getAccepted() {
		return accepted;
	}


	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	@Override
	public String toString() {
		return "Offer [offerId=" + offerId + ", item=" + item + ", customerOffer=" + customerOffer + ", " + accepted + ", " + customer + "]";
	}





}
