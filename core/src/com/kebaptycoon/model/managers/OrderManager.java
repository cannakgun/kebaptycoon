package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Customer;
import com.kebaptycoon.model.entities.Employee;
import com.kebaptycoon.model.entities.Order;
import com.kebaptycoon.model.entities.Recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrderManager {

    private HashMap<Customer, Order> newOrders;
    private HashMap<Customer, Order> processOrders;
    private HashMap<Order, Employee> employees;

    private ReportManager reportManager;

    public OrderManager() {
        newOrders = new HashMap<Customer, Order>();
        processOrders = new HashMap<Customer, Order>();
        employees = new HashMap<Order, Employee>();

        reportManager = new ReportManager();
    }

    public OrderManager(ReportManager rm) {
        newOrders = new HashMap<Customer, Order>();
        processOrders = new HashMap<Customer, Order>();
        employees = new HashMap<Order, Employee>();

        reportManager = rm;
    }

    public void order(Customer customer, Recipe recipe) {
        Order n = new Order(recipe, customer);
        newOrders.put(customer, n);

        reportManager.addDailyOrder(n);
    }

    public void abortOrder(Customer customer) {
        Order o = processOrders.get(customer);
        if (o != null) {
            processOrders.remove(customer);

            Employee e = employees.get(o);

            if(e != null) {
                e.onCancelOrder();
                employees.remove(o);
            }
        }
        o = newOrders.get(customer);
        if (o != null) {
            newOrders.remove(customer);

            Employee e = employees.get(o);

            if(e != null) {
                e.onCancelOrder();
                employees.remove(o);
            }
        }
    }

    public Order getOrderForProcessing(Employee employee) {
        Iterator<Map.Entry<Customer, Order>> it = newOrders.entrySet().iterator();

        if(!it.hasNext()) return null;

        Map.Entry<Customer, Order> ent = it.next();

        processOrders.put(ent.getKey(), ent.getValue());
        newOrders.remove(ent.getKey());
        employees.put(ent.getValue(), employee);

        return ent.getValue();
    }

    public void changeOrderEmployee(Order order, Employee employee) {
        employees.remove(order);
        employees.put(order, employee);
    }

    public Employee getEmployeeOfOrder(Order order) {
        return employees.get(order);
    }
}
