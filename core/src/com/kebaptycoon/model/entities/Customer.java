package com.kebaptycoon.model.entities;

import java.util.ArrayList;

public class Customer extends Person{

	public class Type {

        String              spriteName;
		int 				waitingTime;
		int 				budget;
		ArrayList<String> 	likes;
		ArrayList<String> 	dislikes;

        Type() {
        }

		Type(String spriteName, int waitingTime, int budget, ArrayList<String> likes, ArrayList<String> dislikes) {
			this.waitingTime = waitingTime;
			this.budget = budget;
			this.likes = likes;
			this.dislikes = dislikes;
		}

        public String getSpriteName() {
            return spriteName;
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
	private boolean markedForDeletion;
	
	public Customer(int speed, String spriteName, Type type, int waitingTime, int budget) {
		super(speed,spriteName);
		this.type = type;
		this.waitingTime = waitingTime;
		this.budget = budget;
		this.dish = null;
		this.waitOverride = false;
		this.state = State.WaitForTable;
        this.markedForDeletion = false;
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

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    public void setMarkedForDeletion(boolean markedForDeletion) {
        this.markedForDeletion = markedForDeletion;
    }
}
