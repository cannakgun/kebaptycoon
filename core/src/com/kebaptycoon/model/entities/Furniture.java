package com.kebaptycoon.model.entities;

public class Furniture extends Entity{
	
	public static enum Type{
		Cooker,
		Oven,
		Frier,
		ServingTable,
		Table,
		Decoration,
		Grill
	}
    String description;
    String name;
    Type type;
    Orientation orientation;
    int width;
    int height;
    Person user;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }
}
