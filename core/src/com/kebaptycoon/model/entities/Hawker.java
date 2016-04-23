package com.kebaptycoon.model.entities;

public class Hawker extends Employee{

    public static final int PREPARE_TIME = 250;

	public static enum State{
		Wait,
		PrepareOrder,
		DeliverOrder
	}
	
	private Order 	currentOrder;
	private Dish 	currentDish;
	private State 	state;
    private int     prepareDuration;
	
	public Hawker(int speed, String spriteName)
	{
		super(speed,spriteName);
		this.currentOrder = null;
		this.state = State.Wait;
	}
	
	public Hawker(int speed, String spriteName, int level)
	{
		super(speed,spriteName, level);
		this.currentOrder = null;
		this.state = State.Wait;
	}

    @Override
    public void onCancelOrder() {
        currentOrder = null;
        currentDish = null;
        this.state = State.Wait;
    }

	public Order getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

	public Dish getCurrentDish() {
		return currentDish;
	}

	public void setCurrentDish(Dish currentDish) {
		this.currentDish = currentDish;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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
            case DeliverOrder:
                onDeliverOrder(venue);
                break;
        }
    }

    private void onWait(Venue venue) {
        animationState = AnimationState.Standing;
        Order ord = venue.getOrderManager().getOrderForProcessing(this);

        if(ord == null) {
            Furniture cart = venue.getFurnitures(Furniture.Type.FoodCart).get(0);

            if(cart == null) return;

            if(cart.getPosition().dst(getPosition()) <= 1) {
                resetCurrentPath();
                use(cart);
                return;
            }

            if(currentPath.size() <= 0) {
                currentPath = venue.findPath(getPosition(), cart);
            }
            return;
        }

        currentOrder = ord;
        state = State.PrepareOrder;
        prepareDuration = 0;
    }

    private void onPrepareOrder(Venue venue) {
        if(usedFurniture == null) {
            Furniture cart = venue.getFurnitures(Furniture.Type.FoodCart).get(0);

            if(cart == null) return;

            if(cart.getPosition().dst(getPosition()) <= 1) {
                resetCurrentPath();
                use(cart);
                animationState = AnimationState.Standing;
                return;
            }

            if(currentPath.size() <= 0) {
                currentPath = venue.findPath(getPosition(),
                        cart.getPosition().cpy().add(cart.getOrientation().getReverse().getUnitVector()), 1);
            }

            followPath();
        }
        else {
            if(++prepareDuration >= PREPARE_TIME) {
                stopUsing(usedFurniture);
                currentDish = new Dish(getPosition(), currentOrder.getRecipe(), 500);
                currentOrder.getOrderer().setWaitOverride(true);
                state = State.DeliverOrder;
            }
        }
    }

    private void onDeliverOrder(Venue venue) {
        stopUsing(usedFurniture);
        if (currentDish != null) {
            Customer target = currentOrder.getOrderer();

            if (target == null) return;

            if (target.getPosition().dst(getPosition()) <= 1) {
                target.setDish(currentDish);
                currentDish = null;
                return;
            }

            if(currentPath.size() <= 0) {
                currentPath = venue.findPath(getPosition(), target.getPosition(), 1);
                currentPath.remove(currentPath.size()-1);
            }

            followPath();
        }
        else {
            Furniture cart = venue.getFurnitures(Furniture.Type.FoodCart).get(0);

            if(cart == null) return;

            if(cart.getPosition().dst(getPosition()) <= 1) {
                resetCurrentPath();
                animationState = AnimationState.Standing;
                state = State.Wait;
                return;
            }

            if(currentPath.size() <= 0) {
                currentPath = venue.findPath(getPosition(), cart.getPosition(), 1);
            }

            followPath();
        }
    }
}
