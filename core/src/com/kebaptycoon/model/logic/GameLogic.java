package com.kebaptycoon.model.logic;

import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.*;
import com.kebaptycoon.model.managers.*;
import com.kebaptycoon.utils.ResourceManager;
import com.sun.org.apache.xpath.internal.operations.Or;

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
        marketManager = new MarketManager();
        venueManager = new VenueManager();
        animationManager = new AnimationManager(resourceManager);
        paused = false;
        afterHours = false;
        resetDayTime();

        Venue inonu = new Venue(30, 30, 5, 5, false,
                resourceManager.textures.get("restaurants_inonu"), this, new Vector3(10, 0, 0));

        Furniture newTable = new Furniture();
        newTable.setName("table");
        newTable.setPosition(new Vector3(30, 0, 0));
        newTable.setWidth(1);
        newTable.setHeight(1);
        newTable.setMaximumUsers(4);
        newTable.setOrientation(Orientation.East);
        newTable.setType(Furniture.Type.Table);
        newTable.getUserPositions().add(new Vector3(-.7f, 0, 0));
        newTable.getUserPositions().add(new Vector3(0, .7f, 0));
        newTable.getUserPositions().add(new Vector3(.7f, 0, 0));
        newTable.getUserPositions().add(new Vector3(0, -.7f, 0));

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture();
        newTable.setName("table");
        newTable.setPosition(new Vector3(30, 5, 0));
        newTable.setWidth(1);
        newTable.setHeight(1);
        newTable.setMaximumUsers(4);
        newTable.setOrientation(Orientation.East);
        newTable.setType(Furniture.Type.Table);
        newTable.getUserPositions().add(new Vector3(-.7f, 0, 0));
        newTable.getUserPositions().add(new Vector3(0, .7f, 0));
        newTable.getUserPositions().add(new Vector3(.7f, 0, 0));
        newTable.getUserPositions().add(new Vector3(0, -.7f, 0));

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture();
        newTable.setName("table");
        newTable.setPosition(new Vector3(30, -5, 0));
        newTable.setWidth(1);
        newTable.setHeight(1);
        newTable.setMaximumUsers(4);
        newTable.setOrientation(Orientation.East);
        newTable.setType(Furniture.Type.Table);
        newTable.getUserPositions().add(new Vector3(-.7f, 0, 0));
        newTable.getUserPositions().add(new Vector3(0, .7f, 0));
        newTable.getUserPositions().add(new Vector3(.7f, 0, 0));
        newTable.getUserPositions().add(new Vector3(0, -.7f, 0));

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture();
        newTable.setName("table");
        newTable.setPosition(new Vector3(25, 0, 0));
        newTable.setWidth(1);
        newTable.setHeight(1);
        newTable.setMaximumUsers(4);
        newTable.setOrientation(Orientation.East);
        newTable.setType(Furniture.Type.Table);
        newTable.getUserPositions().add(new Vector3(-.7f, 0, 0));
        newTable.getUserPositions().add(new Vector3(0, .7f, 0));
        newTable.getUserPositions().add(new Vector3(.7f, 0, 0));
        newTable.getUserPositions().add(new Vector3(0, -.7f, 0));

        inonu.getFurnitures().add(newTable);

        Furniture cart = new Furniture();
        cart.setName("foodCart");
        cart.setPosition(new Vector3(35, 0, 0));
        cart.setRender3DDelta(new Vector3(-2, 0, 0));
        cart.setWidth(1);
        cart.setHeight(1);
        cart.setMaximumUsers(1);
        cart.setOrientation(Orientation.East);
        cart.setType(Furniture.Type.FoodCart);
        cart.getUserPositions().add(new Vector3(.7f, 0, 0));

        inonu.getFurnitures().add(cart);

        inonu.incrementIngredient(Ingredient.MincedMeat, 9999);

        Hawker reis = new Hawker(15, "defaultPerson");
        reis.setPosition(new Vector3(40, 0, 0));

        inonu.getEmployees().add(reis);

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

                venue.purgeCustomers();
            }
        }
    }

    private boolean isAfterHours() {
        return time >= 10*60*60;
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

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
