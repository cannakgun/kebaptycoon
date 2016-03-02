package com.kebaptycoon.model.entities;

import java.util.ArrayList;

public class Venue {

	private ArrayList<IngredientTuple> 	stock;
	private ArrayList<Employee> 		employees;
	private ArrayList<Furniture> 		furnitures;
	private int							width;
	private int							height;
	private int							kitchenWidth;
	private int							kitchenHeight;
	private boolean						managed;
	private boolean						operational;
	
	public Venue(int width, int height, int kitchenWidth, int kitchenHeight, boolean managed) {
		this.width = width;
		this.height = height;
		this.kitchenWidth = kitchenWidth;
		this.kitchenHeight = kitchenHeight;
		this.managed = managed;
	}

	public ArrayList<IngredientTuple> getStock() {
		return stock;
	}

	public void setStock(ArrayList<IngredientTuple> stock) {
		this.stock = stock;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}

	public ArrayList<Furniture> getFurnitures() {
		return furnitures;
	}

	public void setFurnitures(ArrayList<Furniture> furnitures) {
		this.furnitures = furnitures;
	}

	public boolean isManaged() {
		return managed;
	}

	public void setManaged(boolean managed) {
		this.managed = managed;
	}

	public boolean isOperational() {
		return operational;
	}

	public void setOperational(boolean operational) {
		this.operational = operational;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getKitchenWidth() {
		return kitchenWidth;
	}

	public int getKitchenHeight() {
		return kitchenHeight;
	}
	
}
