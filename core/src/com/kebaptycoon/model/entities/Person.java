package com.kebaptycoon.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.HashMap;

public class Person extends Entity{

    public static final float SPEED_SCALE = 0.05f;

	public enum AnimationState {
		Standing,
		Walking,
		Carrying,
        Sitting
	}

	protected int speed;
	protected Orientation orientation;
	protected AnimationState animationState;
    HashMap<Orientation, HashMap<AnimationState, Animation>> animations = null;
    Furniture usedFurniture = null;
    ArrayList<Vector3> currentPath = new ArrayList<Vector3>();
	
	public Person(int speed, String name) {
        super(name);
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public AnimationState getAnimationState() {
		return animationState;
	}

	public void setAnimationState(AnimationState animationState) {
		this.animationState = animationState;
	}

    public ArrayList<Vector3> getCurrentPath() {
        return currentPath;
    }

    public void resetAnimations()
    {
        animations = new HashMap<Orientation, HashMap<AnimationState, Animation>>();
    }

    public HashMap<Orientation, HashMap<AnimationState, Animation>> getAnimations() {
        return animations;
    }

    @Override
    public TextureRegion getCurrentFrame() {
        return this.animations.get(orientation).get(animationState).getKeyFrame(animationTime);
    }

    public float getAbsoluteSpeed() {
        return speed * SPEED_SCALE;
    }

    public void move(Orientation direction) {
        move(direction, getAbsoluteSpeed());
    }

    public void move(Orientation direction, float disance) {
        this.orientation = direction;
        Vector3 delta = direction.getUnitVector().scl(disance);
        Vector3 total = getPosition().cpy().add(delta);
        setPosition(total);
    }

    public void move(Orientation direction, float distance, float cap) {
        move(direction, Math.min(distance, cap));
    }

    public void followPath() {
        if(currentPath == null || currentPath.size() <= 0) return;

        Vector3 next = currentPath.get(0).cpy();
        next.z = getPosition().z;
        float dist = next.dst(getPosition());
        Vector3 delta = next.cpy().sub(getPosition());
        move(Orientation.fromVector(delta), getAbsoluteSpeed(), dist);
        if(dist < getAbsoluteSpeed())
            currentPath.remove(0);
    }

    public void think(Venue venue) {}

    public boolean use(Furniture furniture) {
        if (usedFurniture != null) return false;

        if(furniture.getUsers().size() < furniture.getMaximumUsers()) {
            furniture.getUsers().add(this);
            usedFurniture = furniture;
            return true;
        }

        return false;
    }

    public boolean stopUsing(Furniture furniture) {

        if (usedFurniture == null) return false;

        if(furniture.getUsers().contains(this)) {
            furniture.getUsers().remove(this);
            usedFurniture = null;
            return true;
        }

        return false;
    }
}
