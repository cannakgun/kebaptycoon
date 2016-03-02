package com.kebaptycoon.model.entities;

public class Dish extends Entity{
	
	private Recipe 	recipe;
	private int		remaining;
	
	public Dish(int x, int y, int z, Recipe recipe, int remaining) {
		super(x, y, z);
		this.recipe = recipe;
		this.remaining = remaining;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public int getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
	
	public void decreaseRemaining(int delta) {
		this.remaining -= delta;
	}
}
