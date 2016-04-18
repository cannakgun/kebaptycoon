package com.kebaptycoon.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    Person user;
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

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
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
}
