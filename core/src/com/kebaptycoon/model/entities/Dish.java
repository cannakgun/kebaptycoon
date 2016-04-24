package com.kebaptycoon.model.entities;

import com.badlogic.gdx.math.Vector3;

public class Dish extends Entity{
	
	private Recipe 	recipe;
	private int		remaining;
	
	public Dish(Vector3 position, Recipe recipe, int remaining) {
		super();
		setPosition(position);
		this.recipe = recipe;
		this.remaining = remaining;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public int getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
	
	public void decreaseRemaining(int delta) {
		this.remaining -= delta;
	}

	@Override
    public Vector3 getRender3DDelta() {
        return super.getRender3DDelta().cpy().add(0,0,1);
    }
}
