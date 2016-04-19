package com.kebaptycoon.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

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

    public void move(Orientation direction) {
        this.orientation = direction;
        Vector3 delta = direction.getUnitVector().scl(speed * SPEED_SCALE);
        Vector3 total = getPosition().cpy().add(delta);
        setPosition(total);
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
