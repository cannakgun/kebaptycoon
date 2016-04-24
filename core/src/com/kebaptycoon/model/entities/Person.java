package com.kebaptycoon.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.utils.Globals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Person extends Entity{

    public static final Vector2 DEFAULT_2D_DELTA = new Vector2(-48f, -16f);
    public static final Vector3 DEFAULT_3D_DELTA = new Vector3(0f, 0f, 0f);

    public static final float SPEED_SCALE = 0.15f;

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
        orientation = Orientation.East;
        animationState = AnimationState.Standing;

        setRender2DDelta(DEFAULT_2D_DELTA);
        setRender3DDelta(DEFAULT_3D_DELTA);

        resetAnimationTime();
        incrementAnimationTime((float)Math.random() * Globals.ANIMATION_DURATION_PER_FRAME * 4);
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

    public void resetCurrentPath() {
        currentPath = new ArrayList<Vector3>();
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

    public void move(Orientation direction, float distance) {
        this.animationState = AnimationState.Walking;
        this.orientation = direction;
        Vector3 delta = direction.getUnitVector().scl(distance);
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

    public void wander(final Venue venue) {
        if(currentPath == null || currentPath.size() > 0) return;

        ArrayList<Orientation> possibilities = new ArrayList<Orientation>();
        for (Orientation o: Orientation.values()) {
            if(venue.isPathable(o.getUnitVector().add(getPosition())))
                possibilities.add(o);
        }

        int randIndex = new Random().nextInt(possibilities.size());
        Orientation selection = possibilities.get(randIndex);

        currentPath.add(selection.getUnitVector().add(getPosition()));
    }

    public void think(Venue venue) {
        wander(venue);
        followPath();
    }

    public boolean use(Furniture furniture) {
        if (usedFurniture != null) return false;

        if(furniture.getUserCount() < furniture.getMaximumUsers()) {
            furniture.onUse(this);
            usedFurniture = furniture;
            resetCurrentPath();
            return true;
        }

        return false;
    }

    public boolean stopUsing(Furniture furniture) {

        if (usedFurniture == null) return false;

        if(furniture.isUsedBy(this)) {
            furniture.onStopUse(this);
            usedFurniture = null;
            return true;
        }

        return false;
    }
}
