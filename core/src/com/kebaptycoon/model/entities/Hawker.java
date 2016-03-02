package com.kebaptycoon.model.entities;

public class Hawker extends Employee{
	public static enum State{
		Wait,
		PrepareOrder,
		DeliverOrder
	}
	
	private Order 	currentOrder;
	private Dish 	currentDish;
	private State 	state;
	
	public Hawker(int speed, String spriteName)
	{
		super(speed,spriteName);
		this.currentOrder = null;
		this.state = State.Wait;
	}
	
	public Hawker(int speed, String spriteName, int level)
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
