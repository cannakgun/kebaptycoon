package com.kebaptycoon.model.entities;

public class Person extends Entity{
	
	private int speed;
	private String spriteName;
	
	public Person(int speed, String spriteName) {
		this.speed = speed;
		this.spriteName = spriteName;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getSpriteName() {
		return spriteName;
	}

	public void setSpriteName(String spriteName) {
		this.spriteName = spriteName;
	}
}
