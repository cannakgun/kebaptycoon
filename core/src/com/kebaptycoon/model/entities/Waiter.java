package com.kebaptycoon.model.entities;

public class Waiter extends Employee{
	
	public static enum State{
		Wait,
		DeliverOrder
	}
	
	private Order 	currentOrder;
	private Dish 	currentDish;
	private State 	state;
	
	public Waiter(int speed, String spriteName)
	{
		super(speed,spriteName);
		this.currentOrder = null;
		this.state = State.Wait;
	}
	
	public Waiter(int speed, String spriteName, int level)
	{
		super(speed,spriteName, level);
		this.currentOrder = null;
		this.state = State.Wait;
	}

	public Order getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

	public Dish getCurrentDish() {
		return currentDish;
	}

	public void setCurrentDish(Dish currentDish) {
		this.currentDish = currentDish;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
