package com.kebaptycoon.model.entities;

import java.util.ArrayList;

public class CustomerPack {
	
	private ArrayList<Customer> customers;

	public CustomerPack() {
		this.customers = new ArrayList<Customer>();
	}

	public CustomerPack(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
}
