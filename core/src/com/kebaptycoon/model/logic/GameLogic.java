package com.kebaptycoon.model.logic;

import com.kebaptycoon.model.entities.*;
import com.kebaptycoon.model.managers.*;

import java.util.Calendar;

public class GameLogic {

    private RecipeManager recipeManager;
    private AdvertisementManager advertisementManager;
    private CustomerManager customerManager;
    private MarketManager marketManager;
    private OrderManager orderManager;
    private VenueManager venueManager;
    private Calendar date;
    private int money;

    public GameLogic(){

        recipeManager = new RecipeManager();
        advertisementManager = new AdvertisementManager();
        customerManager = new CustomerManager();
        marketManager = new MarketManager();
        orderManager = new OrderManager();
        venueManager = new VenueManager();
        date = new Calendar.Builder().setTimeOfDay(6, 0, 0).build();
    }

    public void update() {
        if(date.get(Calendar.HOUR) >= 18) {
            //After hours
        }
        else {
            //Working hours
            date.add(Calendar.MINUTE, 1);

            for (Venue venue: venueManager.getVenueList()) {

                //Execute person AI
                for (Customer customer: venue.getCustomers()) {
                    customer.think();
                }

                for (Employee employee: venue.getEmployees()) {
                    employee.think();
                }
            }

            //Customer generation


        }

    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public AdvertisementManager getAdvertisementManager() {
        return advertisementManager;
    }

    public CustomerManager getCustomerManager() {
        return customerManager;
    }

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public VenueManager getVenueManager() {
        return venueManager;
    }

    public int getMoney() {

        return money;
    }

    public Calendar getDate() {
        return date;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
