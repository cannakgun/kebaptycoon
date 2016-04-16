package com.kebaptycoon.model.entities;

import java.util.ArrayList;

public class Customer extends Person{

	public enum Type {
		genc(5,50,null, null)
		;//TODO: Create customer archetypes

		private int 				waitingTime;
		private int 				budget;
		private ArrayList<String> 	likes;
		private ArrayList<String> 	dislikes;
		
		private Type(int waitingTime, int budget, ArrayList<String> likes, ArrayList<String> dislikes) {
			this.waitingTime = waitingTime;
			this.budget = budget;
			this.likes = likes;
			this.dislikes = dislikes;
		}

		public int getWaitingTime() {
			return waitingTime;
		}

		public int getBudget() {
			return budget;
		}

		public ArrayList<String> getLikes() {
			return likes;
		}

		public ArrayList<String> getDislikes() {
			return dislikes;
		}
	}
	
	public enum State {
		WaitForTable,
		GoToTable,
		Order,
		WaitForFood,
		EatFood,
		EvaluateFood,
		WaitPack,
		Pay,
		Leave
	}
	
	private Type type;
	private int waitingTime;
	private int budget;
	private Dish dish;
	private boolean waitOverride;
	private State state;
	
	public Customer(int speed, String spriteName, Type type, int waitingTime, int budget) {
		super(speed,spriteName);
		this.type = type;
		this.waitingTime = waitingTime;
		this.budget = budget;
		this.dish = null;
		this.waitOverride = false;
		this.state = State.WaitForTable;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public boolean isWaitOverride() {
		return waitOverride;
	}

	public void setWaitOverride(boolean waitOverride) {
		this.waitOverride = waitOverride;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Type getType() {
		return type;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public int getBudget() {
		return budget;
	}
}
