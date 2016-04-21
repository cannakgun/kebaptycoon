package com.kebaptycoon.model.entities;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Customer extends Person{

	public class Type {

        String              spriteName;
		int 				waitingTime;
		int 				budget;
		ArrayList<String> 	likes;
		ArrayList<String> 	dislikes;

        Type() {
        }

		Type(String spriteName, int waitingTime, int budget, ArrayList<String> likes, ArrayList<String> dislikes) {
			this.waitingTime = waitingTime;
			this.budget = budget;
			this.likes = likes;
			this.dislikes = dislikes;
		}

        public String getSpriteName() {
            return spriteName;
        }

        public int getWaitingTime() {
			return waitingTime;
		}

		public int getBudget() {
			return budget;
		}

		public ArrayList<String> getLikes() {
			return likes;
		}

		public ArrayList<String> getDislikes() {
			return dislikes;
		}
	}
	
	public enum State {
		WaitForTable,
		GoToTable,
		Order,
		WaitForFood,
		EatFood,
		EvaluateFood,
		WaitPack,
		Pay,
		Leave
	}
	
	private Type type;
	private int waitingTime;
	private int budget;
    private int waitDuration;
	private Dish dish;
	private boolean waitOverride;
	private State state;
	private boolean markedForDeletion;
    private CustomerPack pack;
	
	public Customer(int speed, String spriteName, Type type, int waitingTime, int budget) {
		super(speed,spriteName);
		this.type = type;
		this.waitingTime = waitingTime;
		this.budget = budget;
		this.dish = null;
		this.waitOverride = false;
        this.waitDuration = 0;
		this.state = State.WaitForTable;
        this.markedForDeletion = false;
        pack = null;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public boolean isWaitOverride() {
		return waitOverride;
	}

	public void setWaitOverride(boolean waitOverride) {
		this.waitOverride = waitOverride;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Type getType() {
		return type;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public int getBudget() {
		return budget;
	}

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    public void setMarkedForDeletion(boolean markedForDeletion) {
        this.markedForDeletion = markedForDeletion;
    }

    @Override
    public void think(Venue venue) {
        if (pack == null) {
            //Find the pack this guy belongs to
            pack = venue.customers.stream()
                    .filter(new Predicate<CustomerPack>() {
                        @Override
                        public boolean test(CustomerPack customerPack) {
                            return customerPack.getCustomers().contains(this);
                        }
                    }).findFirst().get();
        }

        //If no pack think fails
        if (pack == null) return;

        switch (state) {
            case WaitForTable:
                onWaitForTable(venue);
            case GoToTable:
                onGoToTable(venue);
            case Order:
                onOrder(venue);
            case WaitForFood:
                onWaitForFood(venue);
            case EatFood:
                onEatFood(venue);
            case EvaluateFood:
                onEvaluateFood(venue);
            case WaitPack:
                onWaitPack(venue);
            case Pay:
                onPay(venue);
            case Leave:
                onLeave(venue);
        }
    }

    private void onWaitForTable(Venue venue) {
        Furniture table = venue.getTableManager().getTableFor(pack);

        if (table == null) {
            wander(venue);
            followPath();
        }
        else {
            state = State.GoToTable;
        }
    }

    private void onGoToTable(Venue venue) {
        if(currentPath == null) return;

        Furniture table = venue.getTableManager().getTableFor(pack);

        if(table == null) return;

        if(table.getPosition().dst(getPosition()) <= 1) {
            use(table);
            animationState = AnimationState.Sitting;
            state = State.Order;
            return;
        }

        if(currentPath.size() <= 0) {
            currentPath = venue.findPath(getPosition(), table.getPosition());
        }

        followPath();
    }

    private void onOrder(Venue venue) {
        ArrayList<Recipe> available = venue.getAvailableRecipes();
        if (available.size() <= 0) {
            state = State.Leave;
            return;
        }
        available.removeIf(new Predicate<Recipe>() {
            @Override
            public boolean test(Recipe recipe) {
                return recipe.price > budget;
            }
        });

        ArrayList<Recipe> likes = new ArrayList<Recipe>(available);
        likes.removeIf(new Predicate<Recipe>() {
            @Override
            public boolean test(Recipe recipe) {
                return type.getLikes().contains(recipe.getName());
            }
        });

        if(likes.size() <= 0) likes = available;

        int size = likes.size();
        int rand = new Random().nextInt(size);

        venue.getOrderManager().order(this, likes.get(rand));
        state = State.WaitForFood;
    }

    private void onWaitForFood(Venue venue) {
        if(dish != null) {
            state = State.EatFood;
            venue.getOrderManager().abortOrder(this);
            return;
        }

        waitDuration++;

        if(waitDuration > waitingTime && !waitOverride) {
            state = State.Leave;
            venue.getOrderManager().abortOrder(this);
        }
    }

    private void onEatFood(Venue venue) {
        if(dish.getRemaining() > 0)
            dish.setRemaining(dish.getRemaining() - 1);
        else
            state = State.EvaluateFood;

    }

    private void onEvaluateFood(Venue venue) {
        state = State.WaitPack;
    }

    private void onWaitPack(Venue venue) {
        State[] stateArr = {State.EvaluateFood, State.WaitPack, State.Pay, State.Leave};
        List<State> states = Arrays.asList(stateArr);

        for (Customer friend: pack.getCustomers()){
            if(!states.contains(friend.getState()))
                return;
        }

        state = State.Pay;
    }

    private void onPay(Venue venue) {
        venue.getPaid(dish.getRecipe().getPrice());
        state = State.Leave;
    }

    private void onLeave(Venue venue) {
        venue.getTableManager().leaveTable(pack);
        markedForDeletion = true;

        if(currentPath == null) return;

        Vector3 exit = venue.spawnPosition;

        if(currentPath.size() <= 0) {
            currentPath = venue.findPath(getPosition(), exit);
        }

        followPath();
    }
}
