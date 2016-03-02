package com.kebaptycoon.model.entities;

/*
 * IngredientTuple.java
 * 
 * A simple class for holding an ingredient and a number together.
 * 
 * Useful for representing how much an ingredient a recipe uses or
 * how much a restaurant has stock of an ingredient.
 */

public class IngredientTuple {
	public int count;
	public Ingredient ingredient;
	
	public IngredientTuple(int count, Ingredient ingredient) {
		this.count = count;
		this.ingredient = ingredient;
	}
}
