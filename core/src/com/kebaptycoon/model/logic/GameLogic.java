package com.kebaptycoon.model.logic;

import com.kebaptycoon.model.entities.*;
import com.kebaptycoon.model.managers.*;
import com.kebaptycoon.utils.ResourceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Predicate;

public class GameLogic {

    //Managers
    private ResourceManager resourceManager;
    private RecipeManager recipeManager;
    private AdvertisementManager advertisementManager;
    private CustomerManager customerManager;
    private MarketManager marketManager;
    private OrderManager orderManager;
    private VenueManager venueManager;
    private AnimationManager animationManager;

    //Global game data
    private Calendar date;
    private int money;
    private int level;

    //Vars
    private boolean paused;
    private boolean afterHours;

    public GameLogic(ResourceManager resourceManager){
        this.resourceManager = resourceManager;
        recipeManager = new RecipeManager();
        advertisementManager = new AdvertisementManager();
        customerManager = new CustomerManager();
        marketManager = new MarketManager();
        orderManager = new OrderManager();
        venueManager = new VenueManager();
        animationManager = new AnimationManager(resourceManager);
        date = new Calendar.Builder().setTimeOfDay(6, 0, 0).build();
        afterHours = false;
    }

    public void update() {
        //Game is paused, no game logic
        if(paused) return;

        //After hours, no game logic, but
        if(date.get(Calendar.HOUR) >= 18) {
            if (afterHours) {
                return;
            }
            else {
                OnDayEnd();
                afterHours = true;
                return;
            }
        }

        //Working hours
        else {
            date.add(Calendar.MINUTE, 1);
            afterHours = false;

            //Customer generation
            customerManager.generateCustomers(venueManager.getVenueList());

            for (Venue venue: venueManager.getVenueList()) {

                //Execute person AI
                for (CustomerPack customerPack: venue.getCustomers()) {
                    for(Customer customer: customerPack.getCustomers()) {
                        customer.think();
                    }
                }

                for (Employee employee: venue.getEmployees()) {
                    employee.think();
                }
            }


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

    private void OnDayEnd()
    {
        processAdvertisements();
    }

    private void processAdvertisements() {
        ArrayList<Advertisement> updateList = new ArrayList<Advertisement>(advertisementManager.getAdvertisementList());

        for(Advertisement ad: advertisementManager.getAdvertisementList()) {
            //for
            ad.incrementElapsedDuration();
        }
    }

    public ArrayList<Recipe> getAvailableRecipes()
    {
        ArrayList<Recipe> r = new ArrayList<Recipe>(recipeManager.getRecipes());
        r.removeIf(new Predicate<Recipe>() {
            @Override
            public boolean test(Recipe recipe) {
                return !(recipe.isAvailable() || level > recipe.getMinLevel());
            }
        });
        return r;
    }

    public ArrayList<Recipe> getUnlockedRecipes()
    {
        ArrayList<Recipe> r = new ArrayList<Recipe>(recipeManager.getRecipes());
        r.removeIf(new Predicate<Recipe>() {
            @Override
            public boolean test(Recipe recipe) {
                return level <= recipe.getMinLevel();
            }
        });
        return r;
    }

    public Calendar getDate() {
        return date;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
