package com.kebaptycoon.model.entities;

import java.util.ArrayList;

import com.kebaptycoon.utils.Pair;

public class Venue {

	ArrayList<Pair<Ingredient,Integer>> 	stock;
	ArrayList<Employee> 					employees;
    ArrayList<Furniture> 					furnitures;
    ArrayList<Customer> 					customers;
	int										width;
	int										height;
	int										kitchenWidth;
	int										kitchenHeight;
	boolean									managed;
	boolean									operational;
	
	public Venue(int width, int height, int kitchenWidth, int kitchenHeight, boolean managed) {
		this.width = width;
		this.height = height;
		this.kitchenWidth = kitchenWidth;
		this.kitchenHeight = kitchenHeight;
		this.managed = managed;
	}

	public ArrayList<Pair<Ingredient,Integer>> getStock() {
		return stock;
	}

	public void setStock(ArrayList<Pair<Ingredient,Integer>> stock) {
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

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
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

    /**
     * Tries to increment the given ingredient by given number.
     * Doesn't allow negative stock.
     * @param ing Type of the ingredient to be changed
     * @param delta Amount of the ingredient to be changed, can be negative
     * @return Whether the operation is successful
     */
	public boolean incrementIngredient(Ingredient ing, int delta) {
        //Find the index of given ingredient
		int ind = -1;
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getLeft() == ing) {
				ind = i;
				break;
			}
		}


		if (ind == -1) { //If ingredient does not exists
            if (delta >= 0)
            {
                stock.add(new Pair<Ingredient, Integer>(ing, delta));
                return true;
            }
            else
                return false;
		}
        else { //If ingredient does exist
            if (stock.get(ind).right + delta < 0)
                return false;
            else {
                stock.get(ind).right += delta;
                return true;
            }
        }
	}
	
}
