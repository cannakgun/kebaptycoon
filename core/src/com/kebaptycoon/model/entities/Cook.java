package com.kebaptycoon.model.entities;

public class Cook extends Employee{
	
	public static enum State{
		Wait,
		PrepareOrder
	}
	
	private Order currentOrder;
	private State state;
	
	public Cook(int speed, String spriteName)
	{
		super(speed,spriteName);
		this.currentOrder = null;
		this.state = State.Wait;
	}
	
	public Cook(int speed, String spriteName, int level)
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
