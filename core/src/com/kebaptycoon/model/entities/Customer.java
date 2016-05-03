package com.kebaptycoon.model.entities;

import com.kebaptycoon.model.managers.SoundManager;
import com.kebaptycoon.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Customer extends Person{
	
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
	
	private CustomerType type;
	private int waitingTime;
	private int budget;
    private int waitDuration;
	private Dish dish;
	private boolean waitOverride;
	private State state;
	private boolean markedForDeletion;
    private CustomerPack pack;

	public Customer(int speed, String spriteName, CustomerType type, int waitingTime, int budget) {
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
        resetCurrentPath();
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
        dish.setPosition(getPosition().cpy().add(getRender3DDelta())
                .add(orientation.getUnitVector().scl(0.2f)));
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

	public CustomerType getType() {
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
            for (CustomerPack p: venue.getCustomers()) {
                if(p.getCustomers().contains(this)) {
                    pack = p;
                    break;
                }
            }
        }

        //If no pack think fails
        if (pack == null) return;

        switch (state) {
            case WaitForTable:
                onWaitForTable(venue);
                break;
            case GoToTable:
                onGoToTable(venue);
                break;
            case Order:
                onOrder(venue);
                break;
            case WaitForFood:
                onWaitForFood(venue);
                break;
            case EatFood:
                onEatFood(venue);
                break;
            case EvaluateFood:
                onEvaluateFood(venue);
                break;
            case WaitPack:
                onWaitPack(venue);
                break;
            case Pay:
                onPay(venue);
                break;
            case Leave:
                onLeave(venue);
                break;
        }
    }

    private void onWaitForTable(Venue venue) {
        Furniture table = venue.getTableManager().getTableFor(pack);

        if (table != null) {
            state = State.GoToTable;
        }
    }

    private void onGoToTable(Venue venue) {
        if(currentPath == null) return;

        Furniture table = venue.getTableManager().getTableFor(pack);

        if(table == null) {
            state = State.WaitForTable;
            return;
        }

        if(table.getPosition().dst(getPosition()) <= 1) {
            resetCurrentPath();
            use(table);
            animationState = AnimationState.Sitting;
            state = State.Order;
            return;
        }

        if(currentPath.size() <= 0) {
            currentPath = venue.findPath(getPosition(), table.getPosition(), 1f);
        }

        followPath();
    }

    private void onOrder(Venue venue) {
        ArrayList<Recipe> available = venue.getAvailableRecipes();
        ArrayList<Recipe> budget = new ArrayList<Recipe>();

        for (Recipe r: available) {
            if (r.price <= getBudget())
                budget.add(r);
        }

        if (budget.size() <= 0) {
            createEmotion(Emotion.Type.Expensive);
            state = State.Leave;

            for (Customer friend: pack.getCustomers()){
                friend.state = State.Leave;
            }

            return;
        }

        ArrayList<Recipe> likes = new ArrayList<Recipe>();

        for (Recipe r: budget) {
            if (type.getLikes().contains(r.getName()))
                likes.add(r);
        }


        if(likes.size() <= 0) likes = available;

        int size = likes.size();
        int rand = new Random().nextInt(size);

        venue.getOrderManager().order(this, likes.get(rand));
        state = State.WaitForFood;

        for (Customer friend: pack.getCustomers()){
            friend.waitDuration = 0;
        }

    }

    private void onWaitForFood(Venue venue) {
        if(dish != null) {
            state = State.EatFood;
            venue.getOrderManager().abortOrder(this);
            return;
        }

        waitDuration++;

        for (Customer friend: pack.getCustomers()){
            if (friend == this)
                continue;

            if(friend.waitOverride == true)
                return;
        }

        if(waitDuration > waitingTime && !waitOverride) {
            state = State.Leave;
            venue.getOrderManager().abortOrder(this);
            createEmotion(Emotion.Type.Time);
        }
    }

    private void onEatFood(Venue venue) {
        if(dish.getRemaining() > 0){
            dish.setRemaining(dish.getRemaining() - 15);
        }
        else {
            state = State.EvaluateFood;
        }
    }

    private void onEvaluateFood(Venue venue) {
        float score = rateDish(dish);
        if (score < .5f)
            createEmotion(Emotion.Type.Sad);
        else if (score < .8f)
            createEmotion(Emotion.Type.Happy);
        else
            createEmotion(Emotion.Type.Excited);
        state = State.Pay;
        //TODO: Affect popularity
    }

    private void onPay(Venue venue) {
        venue.getPaid(dish.getRecipe().getPrice());
        state = State.WaitPack;
    }

    private void onWaitPack(Venue venue) {
        State[] stateArr = {State.WaitPack, State.Pay, State.Leave};
        List<State> states = Arrays.asList(stateArr);

        for (Customer friend: pack.getCustomers()){
            if (friend == this)
                continue;

            if(!states.contains(friend.getState()))
                return;
        }
        
        stopUsing(usedFurniture);

        state = State.Leave;
    }

    private void onLeave(Venue venue) {
        dish = null;
        stopUsing(usedFurniture);
        venue.getTableManager().leaveTable(pack);
        markedForDeletion = true;

        if(currentPath == null) return;

        if(currentPath.size() <= 0) {
            currentPath = venue.findPath(getPosition().cpy(), venue.spawnPosition.cpy(), 1);
        }

        followPath();
    }

    public float rateDish(Dish d)
    {
        // 3 customer type -> Teenage, Businessman, Woman
        if (getType().getTextureName().equals("gencAdam")) {
            return ratePrice(d.getRecipe().getPrice()) * (0.6f) + rateIngredients(d.getRecipe()) * (0.3f) + (float) (Math.random() * (0.1f));
        } else if (getType().getTextureName().equals("kadin")) {
            return ratePrice(d.getRecipe().getPrice()) * (0.45f) + rateIngredients(d.getRecipe()) * (0.45f) + (float) (Math.random() * (0.1f));
        } else if (getType().getTextureName().equals("isAdami")) {
            return ratePrice(d.getRecipe().getPrice()) * (0.3f) + rateIngredients(d.getRecipe()) * (0.6f) + (float) (Math.random() * (0.1f));
        } else {
            return (float) Math.random();
        }
    }

    private float ratePrice(int price)
    {

        int half = budget / 2;
        if(half >= price)
            return 1.0f;

        return 1.0f - ((float) (price - half) / half);
    }

    private float rateIngredients(Recipe r)
    {
        float rate = 1.0f;

        ArrayList<Pair<Ingredient,Integer>> optimalIngredients = r.getOptimal();
        ArrayList<Pair<Ingredient,Integer>> actualIngredients = r.getIngredients();
        ArrayList<Float> plusReduces = r.getReduce().getLeft();
        ArrayList<Float> minusReduces = r.getReduce().getRight();

        for(int i = 0; i < optimalIngredients.size(); i++)
        {
            int optimalAmount = optimalIngredients.get(i).getRight();
            int actualAmount = actualIngredients.get(i).getRight();
            float plusReduce = plusReduces.get(i);
            float minusReduce = minusReduces.get(i);

            if(optimalAmount < actualAmount)
                rate += (actualAmount - optimalAmount) * plusReduce;
            if(optimalAmount > actualAmount)
                rate += (optimalAmount - actualAmount) * minusReduce;
        }

        return Math.max(0f, rate);
    }
}
