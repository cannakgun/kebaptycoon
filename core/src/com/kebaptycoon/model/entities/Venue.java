package com.kebaptycoon.model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.logic.GameLogic;
import com.kebaptycoon.model.managers.OrderManager;
import com.kebaptycoon.model.managers.TableManager;
import com.kebaptycoon.utils.Pair;

public class Venue {

    public GameLogic                        gameLogic;

	ArrayList<Pair<Ingredient,Integer>> 	stock;
	ArrayList<Employee> 					employees;
    ArrayList<Furniture> 					furnitures;
    ArrayList<CustomerPack> 				customers;
	int										width;
	int										height;
	int										kitchenWidth;
	int										kitchenHeight;
	boolean									managed;
	boolean									operational;
	public Vector3							spawnPosition;
    public OrderManager 					orderManager;
    public TableManager                     tableManager;
    public Texture                          background;
	
	public Venue(int width, int height, int kitchenWidth, int kitchenHeight, boolean managed,
                 Texture background, GameLogic gameLogic, Vector3 spawn) {
        this.gameLogic = gameLogic;
		stock = new ArrayList<Pair<Ingredient, Integer>>();
		employees = new ArrayList<Employee>();
		furnitures = new ArrayList<Furniture>();
		customers = new ArrayList<CustomerPack>();
		this.width = width;
		this.height = height;
		this.kitchenWidth = kitchenWidth;
		this.kitchenHeight = kitchenHeight;
		this.managed = managed;
		this.operational = true;
        this.orderManager = new OrderManager();
        this.tableManager = new TableManager(this);
        this.background = background;
        this.spawnPosition = spawn;
	}

	public ArrayList<Pair<Ingredient,Integer>> getStock() {
		return stock;
	}

	public void setStock(ArrayList<Pair<Ingredient,Integer>> stock) {
		this.stock = stock;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}

	public ArrayList<Furniture> getFurnitures() {
		return furnitures;
	}

	public void setFurnitures(ArrayList<Furniture> furnitures) {
		this.furnitures = furnitures;
	}

    public ArrayList<CustomerPack> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<CustomerPack> customers) {
        this.customers = customers;
    }

    public boolean isManaged() {
		return managed;
	}

	public void setManaged(boolean managed) {
		this.managed = managed;
	}

	public boolean isOperational() {
		return operational;
	}

	public void setOperational(boolean operational) {
		this.operational = operational;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getKitchenWidth() {
		return kitchenWidth;
	}

	public int getKitchenHeight() {
		return kitchenHeight;
	}

	public Vector3 getSpawnPosition() {
		return spawnPosition;
	}

	public void setSpawnPosition(Vector3 spawnPosition) {
		this.spawnPosition = spawnPosition;
	}

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public TableManager getTableManager() {
        return tableManager;
    }

    public Texture getBackground() {
        return background;
    }

    /**
     * Tries to increment the given ingredient by given number.
     * Doesn't allow negative stock.
     * @param ing Type of the ingredient to be changed
     * @param delta Amount of the ingredient to be changed, can be negative
     * @return Whether the operation is successful
     */
	public boolean incrementIngredient(Ingredient ing, int delta) {
        //Find the index of given ingredient
		int ind = -1;
		for (int i = 0; i < stock.size(); i++) {
			if (stock.get(i).getLeft() == ing) {
				ind = i;
				break;
			}
		}


		if (ind == -1) { //If ingredient does not exists
            if (delta >= 0)
            {
                stock.add(new Pair<Ingredient, Integer>(ing, delta));
                return true;
            }
            else
                return false;
		}
        else { //If ingredient does exist
            if (stock.get(ind).right + delta < 0)
                return false;
            else {
                stock.get(ind).right += delta;
                return true;
            }
        }
	}

    public boolean isPathable(Vector3 point) {
        for (Furniture furniture:furnitures) {
            if (furniture.contains(point))
                return false;
        }
        return true;
    }

    public ArrayList<Furniture> getFurnitures(final Furniture.Type type) {

        ArrayList<Furniture> r = new ArrayList<Furniture>(getFurnitures());
        r.removeIf(new Predicate<Furniture>() {
            @Override
            public boolean test(Furniture f) {
                return f.getType() != type;
            }
        });
        return r;
    }

    public ArrayList<Vector3> findPath(Vector3 source, Vector3 target)
    {
        return findPath(source, target, 0);
    }

    public ArrayList<Vector3> findPath(Vector3 source, Vector3 target, float margin)
    {
        Comparator<Pair<Vector3, Float>> comparator = new QueueComparator();

        PriorityQueue<Pair<Vector3, Float>> frontier = new PriorityQueue<Pair<Vector3, Float>>(10, comparator);
        frontier.add(new Pair<Vector3, Float>(source, 0f));

        HashMap<Vector3, Vector3> cameFrom = new HashMap<Vector3, Vector3>();
        cameFrom.put(source, null);

        HashMap<Vector3, Float> costSoFar = new HashMap<Vector3, Float>();
        costSoFar.put(source, 0f);

        Vector3 last = target;

        int i = 0;

        while(!frontier.isEmpty())
        {
            final Pair<Vector3, Float> current = frontier.poll();

            if(current.getLeft().dst(target) <= margin){
                last = current.getLeft();
                break;
            }

            for(Vector3 next : getNeighbors(current.getLeft()))
            {
                float newCost = costSoFar.get(current.getLeft()) + 1;

                if((!costSoFar.containsKey(next)) || (newCost < costSoFar.get(next)))
                {
                    costSoFar.put(next, newCost);
                    float priority = newCost + target.dst(next);
                    frontier.add(new Pair<Vector3, Float>(next, priority));
                    cameFrom.put(next, current.getLeft());
                }
            }
        }

        Vector3 current = last;
        ArrayList<Vector3> path = new ArrayList<Vector3>();
        if(!last.equals(target))
            path.add(target);
        path.add(current);


        while(!current.equals(source))
        {
            current = cameFrom.remove(current);
            path.add(current);
        }

        Collections.reverse(path);

        return path;
    }

    public ArrayList<Recipe> getAvailableRecipes() {
        ArrayList<Recipe> all = gameLogic.getAvailableRecipes();
        all.removeIf(new Predicate<Recipe>() {
            @Override
            public boolean test(Recipe recipe) {
                for(Pair<Ingredient, Integer> p: recipe.ingredients) {
                    if (p.right > getStock(p.left))
                        return true;
                }
                return false;
            }
        });
        return all;
    }

    private int getStock(final Ingredient ing) {

        Stream<Pair<Ingredient, Integer>> str = stock.stream()
                .filter(new Predicate<Pair<Ingredient, Integer>>() {
                    @Override
                    public boolean test(Pair<Ingredient, Integer> pair) {
                        return pair.left == ing;
                    }
                });

        if (str.count() <= 0) return 0;

        Pair<Ingredient, Integer> pair = str.findFirst().get();

        if(pair == null)
            return 0;
        else
            return pair.right;
    }

    public void getPaid(int money) {
        gameLogic.setMoney(gameLogic.getMoney() + money);
    }

    private ArrayList<Vector3> getNeighbors(final Vector3 current)
    {
        ArrayList<Orientation> possibilities = new ArrayList<Orientation>(Arrays.asList(Orientation.values()));
        possibilities.removeIf(new Predicate<Orientation>() {
            @Override
            public boolean test(Orientation orientation) {
                return !isPathable(orientation.getUnitVector().add(current));
            }
        });

        ArrayList<Vector3> neighbors = new ArrayList<Vector3>();

        for(Orientation o : possibilities)
        {
            neighbors.add(o.getUnitVector().add(current));
        }

        return neighbors;
    }

    private class QueueComparator implements Comparator<Pair<Vector3, Float>>
    {
        @Override
        public int compare(Pair<Vector3, Float> x, Pair<Vector3, Float> y)
        {
            if (x.getRight() < y.getRight())
            {
                return -1;
            }
            if (x.getRight() > y.getRight())
            {
                return 1;
            }
            return 0;
        }
    }

    public void purgeCustomers() {
        for(CustomerPack p: customers) {
            for (Customer c : new ArrayList<Customer>(p.getCustomers())) {
                if (c.isMarkedForDeletion() && c.getPosition().dst(spawnPosition) < 1)
                    p.getCustomers().remove(c);
            }
        }


        for(CustomerPack p: new ArrayList<CustomerPack>(customers)) {
            if (p.getCustomers().size() <= 0)
                customers.remove(p);
        }
    }
}
