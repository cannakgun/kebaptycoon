package com.kebaptycoon.model.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.utils.IsometricHelper;

public class Entity {
    private Vector3 position;
    private Vector3 render3DDelta;
    private Vector2 render2DDelta;
    private Animation animation;
    private float animationTime;
	
	public Entity() {
        this.position = new Vector3(0f,0f,0f);
        this.render3DDelta = new Vector3(.5f,.5f,0f);
        this.render2DDelta = new Vector2(0f,0f);
        animation = null;
        animationTime = 0f;
	}
	
	public Entity(Vector3 position) {
        this.position = position.cpy();
        this.render3DDelta = new Vector3(.5f,.5f,0f);
        this.render2DDelta = new Vector2(0f,0f);
        animation = null;
        animationTime = 0f;
	}

    public Entity(Vector3 position, Animation animation) {
        this.position = position.cpy();
        this.render3DDelta = new Vector3(.5f,.5f,0f);
        this.render2DDelta = new Vector2(0f,0f);
        this.animation = animation;
        animationTime = 0;
    }

    public Entity(Vector3 position, Animation animation, Vector3 render3DDelta, Vector2 render2DDelta) {
        this.position = position.cpy();
        this.render3DDelta = render3DDelta;
        this.render2DDelta = render2DDelta;
        this.animation = animation;
        animationTime = 0;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getRender3DDelta() {
        return render3DDelta;
    }

    public void setRender3DDelta(Vector3 render3DDelta) {
        this.render3DDelta = render3DDelta;
    }

    public Vector2 getRender2DDelta() {
        return render2DDelta;
    }

    public void setRender2DDelta(Vector2 render2DDelta) {
        this.render2DDelta = render2DDelta;
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

    public void render(SpriteBatch batch, float delta)
    {
        TextureRegion texture = cycleAnimation(delta);
        Vector3 iso = position.cpy().add(render3DDelta);
        Vector2 screen = IsometricHelper.project(iso).add(render2DDelta);
        batch.draw(texture, screen.x, screen.y);
    }
}
