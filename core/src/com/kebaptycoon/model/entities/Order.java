package com.kebaptycoon.model.entities;

public class Order {
	
	public static enum State{
		Kitchen,
		WaitingTable,
		Waiter
	}
	
	private Recipe recipe;
	private int currentProcess;
	private State state;
	private Dish dish;
	private Customer orderer;
	
	public Order(Recipe recipe, int currentProcess, State state, Dish dish, Customer orderer) {
		this.recipe = recipe;
		this.currentProcess = currentProcess;
		this.state = state;
		this.dish = dish;
		this.orderer = orderer;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public int getCurrentProcess() {
		return currentProcess;
	}

	public void setCurrentProcess(int currentProcess) {
		this.currentProcess = currentProcess;
	}
	
	public void incrementCurrentProcess(int delta) {
		this.currentProcess += delta;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public Customer getOrderer() {
		return orderer;
	}

	public void setOrderer(Customer orderer) {
		this.orderer = orderer;
	}
}
