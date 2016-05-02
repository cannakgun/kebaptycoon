package com.kebaptycoon.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.prototypes.FurniturePrototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Furniture extends Entity{

    public static enum Type{
        FoodCart,
        Fridge,
		Cooker,
		Oven,
		Frier,
		ServingTable,
		Table,
		Decoration,
		Grill,
        Blocker
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
    public Queue<Order> buffer = new LinkedList<Order>();

    public Furniture() {}

    public Furniture(FurniturePrototype proto) {
        setOrientation(proto.orientation);
        type = proto.type;
        description = proto.desc;
        setName(proto.name);
        width = proto.width;
        height = proto.height;
        maximumUsers = proto.maximumUsers;
        setRender2DDelta(new Vector2(proto.renderDelta.x, proto.renderDelta.y));
    }

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

    @Override
    public void render(SpriteBatch batch, float delta)
    {
        if (type != Type.Blocker)
            super.render(batch, delta);
    }

    public boolean contains(Vector3 point)
    {
        Vector3 bound = orientation.getReverse().getUnitVector().scl(height)
                .add(orientation.rotateCounterClockwise().getUnitVector().scl(width));

        Vector3 delta = point.cpy().sub(getPosition());

        return isBetween(0f, bound.x, delta.x) && isBetween(0f, bound.y, delta.y);
    }

    private boolean isBetween(float inclusive, float exclusive, float tested){
        if (exclusive > inclusive) {
            return tested < exclusive && tested >= inclusive;
        }
        else {
            return tested > exclusive && tested <= inclusive;
        }
    }

    public Vector3 findUsablePosition() {
        return getLeavePosition(findSuitableOrientation());
    }

    public Vector3 getUsePosition(Orientation o) {
        float scale;
        if(o == orientation || o == orientation.getReverse())
            scale = height;
        else
            scale = width;

        Vector3 center = getRender3DDelta().add(getPosition());

        return center.cpy().add(o.getUnitVector().scl(scale / 2));
    }

    public Vector3 getLeavePosition(Orientation o) {
        float scale;
        if(o == orientation || o == orientation.getReverse())
            scale = height;
        else
            scale = width;

        Vector3 vec = getRender3DDelta().add(getPosition());

        vec.add(o.getUnitVector().scl(scale / 2));

        while(vec.x % 1 != 0 || vec.y % 1 != 0 || contains(vec))
            vec.add(o.getUnitVector().scl(.5f));

        return vec;
    }

    public void onUse(Person person) {
        Orientation o = findSuitableOrientation();

        oldPositions.put(person, person.getPosition());

        person.setPosition(getUsePosition(o));

        person.orientation = o.getReverse();
        slots.put(o, person);
    }

    public void onStopUse(Person person) {
        person.orientation = person.orientation.getReverse();
        person.setPosition(getLeavePosition(person.orientation));
        oldPositions.remove(person);
        slots.remove(person.orientation);
    }

    public void clearUsers() {
        slots.clear();
        oldPositions.clear();
    }
}
