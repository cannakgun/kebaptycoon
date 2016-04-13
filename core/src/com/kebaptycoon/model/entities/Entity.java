package com.kebaptycoon.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity {
	private int x;
	private int y;
	private int z;
    private Animation animation;
    private float animationTime;
	
	public Entity() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
        animation = null;
        animationTime = 0f;
	}
	
	public Entity(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
        animation = null;
        animationTime = 0f;
	}

    public Entity(int x, int y, int z, Animation animation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.animation = animation;
        animationTime = 0;
    }

    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

    public Animation getAnimation() {
        return animation;
    }

    public TextureRegion cycleAnimation(float delta) {
        incrementAnimationTime(delta);
        return getCurrentFrame();
    }

    public TextureRegion getCurrentFrame() {
        return this.animation.getKeyFrame(animationTime);
    }

    public void incrementAnimationTime(float delta) {
        this.animationTime += delta;
    }

    public void resetAnimationTime() {
        this.animationTime = 0f;
    }
}
