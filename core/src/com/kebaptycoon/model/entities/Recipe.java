package com.kebaptycoon.model.entities;

import java.util.ArrayList;

public class Recipe {
	
	private ArrayList<IngredientTuple> 	ingredients;
	private int 						price;
	private ArrayList<Furniture.Type>	process;
	private int 						minLevel;
	private boolean						available;
	
	public Recipe(ArrayList<IngredientTuple> ingredients, int price, ArrayList<Furniture.Type>	process, int minLevel, boolean available) {
		this.ingredients = ingredients;
		this.price = price;
		this.process = process;
		this.minLevel = minLevel;
		this.available = available;
	}

	public ArrayList<IngredientTuple> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<IngredientTuple> ingredients) {
		this.ingredients = ingredients;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ArrayList<Furniture.Type> getProcess() {
		return process;
	}

	public void setProcess(ArrayList<Furniture.Type> process) {
		this.process = process;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getMinLevel() {
		return minLevel;
	}
}
