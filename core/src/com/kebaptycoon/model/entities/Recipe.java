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
}
