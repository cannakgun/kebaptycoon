package com.kebaptycoon.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;

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
    Type type;
    Orientation orientation;
    int width;
    int height;
    int maximumUsers;
    ArrayList<Person> users;
    HashMap<Orientation, Animation> animations = null;

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

    public int getMaximumUsers() {
        return maximumUsers;
    }

    public void setMaximumUsers(int maximumUsers) {
        this.maximumUsers = maximumUsers;
    }

    public ArrayList<Person> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Person> user) {
        this.users = user;
    }

    public HashMap<Orientation, Animation> getAnimations() {
        return animations;
    }

    public void resetAnimations()
    {
        animations = new HashMap<Orientation, Animation>();
    }

    @Override
    public TextureRegion getCurrentFrame() {
        return this.animations.get(orientation).getKeyFrame(animationTime);
    }

    public boolean contains(Vector3 point)
    {
        Vector3 delta = point.cpy().sub(getPosition());
        return (delta.x >= 0 && delta.x <= width) && (delta.y >= 0 && delta.y <= height);
    }
}
