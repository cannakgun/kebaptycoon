package com.kebaptycoon.model.entities;

public class Employee extends Person{

	public static final int MaximumLevel = 10;
	
	private int level;
	private int experience;
	
	public Employee(int speed, String spriteName) {
		super(speed,spriteName);
		this.level = 1;
		this.experience = 0;
	}

	public Employee(int speed, String spriteName, int level) {
		super(speed, spriteName);
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public void onCancelOrder() {}

	public void reset(){
		resetCurrentPath();
		if(usedFurniture != null) {
			stopUsing(usedFurniture);
			usedFurniture = null;
		}
	}
}
