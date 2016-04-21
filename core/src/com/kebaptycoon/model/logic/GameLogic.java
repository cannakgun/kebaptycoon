package com.kebaptycoon.model.logic;

import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.*;
import com.kebaptycoon.model.managers.*;
import com.kebaptycoon.utils.ResourceManager;

import java.util.ArrayList;
import java.util.function.Predicate;

public class GameLogic {

    public static final int START_HOUR = 8;
    public static final int END_HOUR = 18;
    public static final int SECONDS_PER_FRAME = 60;

    //Managers
    private ResourceManager resourceManager;
    private RecipeManager recipeManager;
    private AdvertisementManager advertisementManager;
    private CustomerManager customerManager;
    private MarketManager marketManager;
    private VenueManager venueManager;
    private AnimationManager animationManager;

    //Global game data
    private int time;
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
        //marketManager = new MarketManager();
        venueManager = new VenueManager();
        animationManager = new AnimationManager(resourceManager);
        paused = false;
        afterHours = false;
        resetDayTime();

        Venue inonu = new Venue(30, 30, 5, 5, false,
                resourceManager.textures.get("restaurants_inonu"), this, new Vector3(10, 0, 0));
        venueManager.getVenueList().add(inonu);
    }

    public void update() {
        //Game is paused, no game logic
        if(paused) return;

        //After hours, no game logic, but
        if(isAfterHours()) {
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
            if(afterHours) {
                afterHours = false;
                OnDayStart();
            }

            time++;

            //Customer generation
            customerManager.generateCustomers(venueManager.getVenueList());


            for (Venue venue: venueManager.getVenueList()) {


                //Execute person AI
                for (CustomerPack customerPack: venue.getCustomers()) {
                    animationManager.autoSetUp(customerPack.getCustomers());
                    for(Customer customer: customerPack.getCustomers()) {
                        customer.think(venue);
                    }
                }

                animationManager.autoSetUp(venue.getEmployees());

                for (Employee employee: venue.getEmployees()) {
                    employee.think(venue);
                }

                animationManager.autoSetUp(venue.getFurnitures());
            }
        }
    }

    private boolean isAfterHours() {
        return time/60 >= 10;
    }

    private void resetDayTime() {
        time = 0;
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

    public VenueManager getVenueManager() {
        return venueManager;
    }

    public int getMoney() {

        return money;
    }

    private void OnDayStart() {
    }

    private void OnDayEnd()
    {
        customerManager.decayPopularity();
        processAdvertisements();
    }

    private void processAdvertisements() {
        ArrayList<Advertisement> oldList = new ArrayList<Advertisement>(advertisementManager.getAdvertisementList());

        for(Advertisement ad: oldList) {
            ad.incrementElapsedDuration();
            customerManager.applyAdvertisement(ad);

            if(ad.isExpired())
                advertisementManager.removeAdvertisement(ad);
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

    public void setMoney(int money) {
        this.money = money;
    }
}
