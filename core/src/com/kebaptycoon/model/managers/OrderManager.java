package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Customer;
import com.kebaptycoon.model.entities.Dish;
import com.kebaptycoon.model.entities.Recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrderManager {

    private HashMap<Customer, Recipe> newOrders;
    private HashMap<Customer, Recipe> procesOrders;
    private HashMap<Customer, Dish> dishes;

    public OrderManager() {
        newOrders = new HashMap<Customer, Recipe>();
        procesOrders = new HashMap<Customer, Recipe>();
        dishes = new HashMap<Customer, Dish>();
    }

    public void order(Customer customer, Recipe recipe) {
        newOrders.put(customer,recipe);
    }

    public void abortOrder(Customer customer) {
        newOrders.remove(customer);
        procesOrders.remove(customer);
        dishes.remove(customer);
    }

    public Recipe getOrderForProcessing() {
        Iterator<Map.Entry<Customer, Recipe>> it = newOrders.entrySet().iterator();

        if(!it.hasNext()) return null;

        Map.Entry<Customer, Recipe> ent = it.next();

        procesOrders.put(ent.getKey(), ent.getValue());
        newOrders.remove(ent.getKey());

        return ent.getValue();
    }

    public void markOrderReady(Customer customer, Dish dish) {
        newOrders.remove(customer);
        procesOrders.remove(customer);
        dishes.put(customer, dish);
    }
}
