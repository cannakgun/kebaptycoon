package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Customer;
import com.kebaptycoon.model.entities.Order;
import com.kebaptycoon.model.entities.Recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrderManager {

    private HashMap<Customer, Order> newOrders;
    private HashMap<Customer, Order> processOrders;

    public OrderManager() {
        newOrders = new HashMap<Customer, Order>();
        processOrders = new HashMap<Customer, Order>();
    }

    public void order(Customer customer, Recipe recipe) {
        Order n = new Order(recipe, customer);
        newOrders.put(customer,n);
    }

    public void abortOrder(Customer customer) {
        newOrders.remove(customer);
        processOrders.remove(customer);
    }

    public Order getOrderForProcessing() {
        Iterator<Map.Entry<Customer, Order>> it = newOrders.entrySet().iterator();

        if(!it.hasNext()) return null;

        Map.Entry<Customer, Order> ent = it.next();

        processOrders.put(ent.getKey(), ent.getValue());
        newOrders.remove(ent.getKey());

        return ent.getValue();
    }
}
