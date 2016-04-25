package com.kebaptycoon.model.entities;

import java.util.ArrayList;

public class Waiter extends Employee{
	
	public static enum State{
		Wait,
		DeliverOrder
	}
	
	private Order   	currentOrder;
    private Furniture   currentTable;
	private State 	    state;
	
	public Waiter(int speed, String spriteName)
	{
		super(speed,spriteName);
		this.currentOrder = null;
		this.state = State.Wait;
	}
	
	public Waiter(int speed, String spriteName, int level)
	{
		super(speed,spriteName, level);
		this.currentOrder = null;
		this.state = State.Wait;
	}

	public Order getCurrentOrder() {
		return currentOrder;
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
			case DeliverOrder:
				onDeliverOrder(venue);
				break;
		}
	}

	private void onWait(Venue venue) {

		animationState = AnimationState.Standing;
		ArrayList<Furniture> table = venue.getFurnitures(Furniture.Type.ServingTable);

		for(Furniture t: table) {
            for(Order or: t.buffer) {
                if (!(venue.getOrderManager().getEmployeeOfOrder(or) instanceof Waiter)) {
                    venue.getOrderManager().changeOrderEmployee(or, this);
                    currentOrder = or;
                    currentTable = t;
                    break;
                }
            }
        }

		if(currentOrder != null) {
            currentTable.buffer.remove(currentOrder);
            state = State.DeliverOrder;
		}
	}

	private void onDeliverOrder(Venue venue) {
        if(currentTable != null) {
            if(currentTable.getPosition().dst(getPosition()) <= 1) {
                resetCurrentPath();
                currentTable = null;
                return;
            }

            if(currentPath.size() <= 0) {
                currentPath = venue.findPath(getPosition(), currentTable.getPosition(), 1);
            }

            followPath();
        }
        else{
            Customer target = currentOrder.getOrderer();

            if (target == null) return;

            if (target.getPosition().dst(getPosition()) <= 1) {
				resetCurrentPath();
                target.setDish(currentOrder.getDish());
                currentOrder = null;
                state = State.Wait;
                return;
            }

            if(currentPath.size() <= 0) {
                currentPath = venue.findPath(getPosition(), target.getPosition(), 1);
                currentPath.remove(currentPath.size()-1);
            }

            followPath();
        }

	}

    @Override
    public void reset(){
        currentOrder = null;
        state = State.Wait;
    }
}
