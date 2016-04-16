package com.kebaptycoon.model.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.kebaptycoon.utils.Pair;

public class Recipe {
	
	ArrayList<Pair<Ingredient,Integer>> 	ingredients;
	int 									price;
	ArrayList<Furniture.Type>				process;
	int 									minLevel;
	boolean									available;
	String 									name;
	String									texture;

	public Recipe(){}

	public Recipe(String name, ArrayList<Pair<Ingredient,Integer>> ingredients, int price,
				  ArrayList<Furniture.Type>	process, int minLevel, boolean available, String texture) {
		this.name = name;
		this.ingredients = ingredients;
		this.price = price;
		this.process = process;
		this.minLevel = minLevel;
		this.available = available;
		this.texture = texture;
	}

	public ArrayList<Pair<Ingredient,Integer>> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Pair<Ingredient,Integer>> ingredients) {
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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
	public void print(){
		System.out.println("Name: " + name);
		System.out.println("Price: " + price);
		System.out.println("Min Level: " + minLevel);
		System.out.println("Available: " + isAvailable());
		for (Pair<Ingredient, Integer> ing: ingredients ) {
			System.out.println(ing);
		}
		for (Furniture.Type pro: process ) {
			System.out.println(pro);
		}
	}

	public String getTexture(){
		return texture;
	}

	/**
	 * Tries to increment the given ingredient by given number.
	 * Doesn't allow negative stock or changing nonexistent ingredients.
	 * @param ing Type of the ingredient to be changed
	 * @param delta Amount of the ingredient to be changed, can be negative
	 * @return Whether the operation is successful
	 */
	public boolean incrementIngredient(Ingredient ing, int delta) {
		//Find the index of given ingredient
		int ind = -1;
		for (int i = 0; i < ingredients.size(); i++) {
			if (ingredients.get(i).getLeft() == ing) {
				ind = i;
				break;
			}
		}


		if (ind == -1) {
			return false;
		}
		else { //If ingredient does exist
			if (ingredients.get(ind).right + delta < 0)
				return false;
			else {
				ingredients.get(ind).right += delta;
				return true;
			}
		}
	}
}
