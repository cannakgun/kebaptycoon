package com.kebaptycoon.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;

public class Furniture extends Entity{

    public static enum Type{
        FoodCart,
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
    int width; //Left
    int height; //Back
    int maximumUsers;
    HashMap<Orientation, Person> slots = new HashMap<Orientation, Person>();
    HashMap<Person, Vector3> oldPositions = new HashMap<Person, Vector3>();
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

    @Override
    public Vector3 getRender3DDelta() {
        float h = ((float)height - 1f) / 2;
        float w = ((float)width - 1f) / 2;

        Vector3 hDelta = orientation.getReverse().getUnitVector().scl(h);
        Vector3 wDelta = orientation.rotateCounterClockwise().getUnitVector().scl(w);
        return hDelta.add(wDelta);
    }

    public int getUserCount() {
        return slots.entrySet().size();
    }

    public boolean isUsedBy(Person person) {
        return slots.containsValue(person);
    }

    public Orientation findSuitableOrientation() {
        Orientation[] priority = {orientation.getReverse(), orientation,
                orientation.rotateClockwise(), orientation.rotateCounterClockwise()};

        for(Orientation or: priority) {
            if (!slots.containsKey(or))
                return or;
        }

        return null;
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
        if(orientation == Orientation.North)
            delta = delta.scl(-1);
        else if (orientation == Orientation.East)
            delta = new Vector3(delta.y, -delta.x, delta.z);
        else if(orientation == Orientation.West)
            delta = new Vector3(-delta.y, delta.x, delta.z);

        return (delta.x >= 0 && delta.x < width) && (delta.y >= 0 && delta.y < height);
    }

    public void onUse(Person person) {
        Orientation o = findSuitableOrientation();
        float scale;
        if(o == orientation || o == orientation.getReverse())
            scale = height;
        else
            scale = width;

        Vector3 center = getRender3DDelta().add(getPosition());

        oldPositions.put(person, person.getPosition());

        person.setPosition(center.cpy().add(o.getUnitVector().scl(scale/2)));

        person.orientation = o.getReverse();
        slots.put(o, person);
    }

    public void onStopUse(Person person) {
        person.orientation = person.orientation.getReverse();

        person.setPosition(oldPositions.get(person));
        oldPositions.remove(person);
        slots.remove(person.orientation);
    }
}
