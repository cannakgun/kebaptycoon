package com.kebaptycoon.model.entities;

import com.kebaptycoon.utils.Pair;

public class Cook extends Employee{

    private int prepareDuration;

    public static enum State{
		Wait,
		PrepareOrder
	}
	
	private Order currentOrder;
	private State state;
	
	public Cook(int speed, String spriteName)
	{
		super(speed,spriteName);
		this.currentOrder = null;
		this.state = State.Wait;
	}
	
	public Cook(int speed, String spriteName, int level)
	{
		super(speed,spriteName, level);
		this.currentOrder = null;
		this.state = State.Wait;
	}

	public Order getCurrentOrder() {
		return currentOrder;
	}

    public int getPrepareTime() {
        return 80 - (4 * getLevel());
    }

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

    @Override
    public void onCancelOrder() {
        currentOrder = null;
        this.state = State.Wait;
    }

	@Override
	public void think(Venue venue) {
        switch (state) {
            case Wait:
                onWait(venue);
                break;
            case PrepareOrder:
                onPrepareOrder(venue);
                break;
        }
	}

    private void onWait(Venue venue) {

        animationState = AnimationState.Standing;
        Order ord = venue.getOrderManager().getOrderForProcessing(this);

        if(ord != null) {
            currentOrder = ord;
            state = State.PrepareOrder;
            prepareDuration = 0;
        }
    }

    private void onPrepareOrder(Venue venue) {
        int ind = currentOrder.getCurrentProcess();

        if (ind >= currentOrder.getRecipe().getProcess().size()) {
            Furniture table = venue.getFurnitures(Furniture.Type.ServingTable).get(0);

            if(table == null) return;

            if(table.getPosition().dst(getPosition()) <= 1) {
                resetCurrentPath();
                currentOrder.setDish(new Dish(table.getPosition(), currentOrder.getRecipe(), 500));
                table.buffer.offer(currentOrder);
                currentOrder.getOrderer().setWaitOverride(true);
                currentOrder = null;
                state = State.Wait;
                return;
            }

            if(currentPath.size() <= 0) {
                currentPath = venue.findPath(getPosition(), table.getPosition(), 1);
            }

            followPath();
            return;
        }

        Furniture.Type process = currentOrder.getRecipe().getProcess().get(ind);

        if(usedFurniture == null || usedFurniture.type != process) {
            if(usedFurniture != null) stopUsing(usedFurniture);

            if(venue.getUnusedFurnitures(process).size() <= 0) return;

            Furniture appliance = venue.getUnusedFurnitures(process).get(0);

            if(appliance == null) {
                resetCurrentPath();
                animationState = AnimationState.Standing;
                return;
            }

            if(appliance.findUsablePosition().dst(getPosition()) <= 1) {
                if(use(appliance)) {
                    resetCurrentPath();
                    animationState = AnimationState.Standing;
                    prepareDuration = 0;
                }
                return;
            }

            if(currentPath.size() <= 0) {
                currentPath = venue.findPath(getPosition(), appliance.getPosition(), 1);
            }

            followPath();
            return;
        }
        else {
            if(++prepareDuration >= getPrepareTime()) {
                stopUsing(usedFurniture);
                Recipe currentRecipe = currentOrder.getRecipe();
                for(Pair<Ingredient, Integer> p : currentRecipe.getIngredients())
                    venue.incrementIngredient(p.getLeft(), -p.getRight());
                currentOrder.setCurrentProcess(currentOrder.getCurrentProcess() + 1);
            }
        }

    }
    @Override
    public void reset(){
        super.reset();
        currentOrder = null;
        state = State.Wait;
    }
}
