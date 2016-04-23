package com.kebaptycoon.model.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.*;
import com.kebaptycoon.model.managers.*;
import com.kebaptycoon.utils.ResourceManager;

import java.util.ArrayList;

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
    private FacebookFriendManager facebookFriendManager;
    //Global game data
    private int time;
    private int money;
    private int level;

    //Vars
    private boolean paused;
    private boolean afterHours;

    public GameLogic(ResourceManager resourceManager){
        level = 15;

        this.resourceManager = resourceManager;
        recipeManager = new RecipeManager();
        advertisementManager = new AdvertisementManager();
        customerManager = new CustomerManager();
        marketManager = new MarketManager();
        venueManager = new VenueManager();
        animationManager = new AnimationManager(resourceManager);
        facebookFriendManager = new FacebookFriendManager();

        paused = false;
        afterHours = false;
        resetDayTime();

        venueManager.getVenueList().add(createTutorialVenue());
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

    public FacebookFriendManager getFacebookFriendManager() {
        return facebookFriendManager;
    }

    public Venue createTutorialVenue() {
        Venue inonu = new Venue(30, 30, 5, 5, false,
                resourceManager.textures.get("restaurants_inonu"), this, new Vector3(-3, 0, 0));

        Furniture newTable = new Furniture();
        newTable.setName("table");
        newTable.setPosition(new Vector3(14, -1, 0));
        newTable.setRender3DDelta(new Vector3(0.5f, -0.5f, 0));
        newTable.setRender2DDelta(new Vector2(-63f, -22f));
        newTable.setWidth(1);
        newTable.setHeight(1);
        newTable.setMaximumUsers(4);
        newTable.setOrientation(Orientation.East);
        newTable.setType(Furniture.Type.Table);

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture();
        newTable.setName("table");
        newTable.setPosition(new Vector3(14, 2, 0));
        newTable.setRender3DDelta(new Vector3(0.5f, -0.5f, 0));
        newTable.setRender2DDelta(new Vector2(-63f, -22f));
        newTable.setWidth(1);
        newTable.setHeight(1);
        newTable.setMaximumUsers(4);
        newTable.setOrientation(Orientation.East);
        newTable.setType(Furniture.Type.Table);

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture();
        newTable.setName("table");
        newTable.setPosition(new Vector3(14, -4, 0));
        newTable.setRender3DDelta(new Vector3(0.5f, -0.5f, 0));
        newTable.setRender2DDelta(new Vector2(-63f, -22f));
        newTable.setWidth(1);
        newTable.setHeight(1);
        newTable.setMaximumUsers(4);
        newTable.setOrientation(Orientation.East);
        newTable.setType(Furniture.Type.Table);

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture();
        newTable.setName("table");
        newTable.setPosition(new Vector3(17, 0, 0));
        newTable.setRender3DDelta(new Vector3(0.5f, -0.5f, 0));
        newTable.setRender2DDelta(new Vector2(-63f, -22f));
        newTable.setWidth(1);
        newTable.setHeight(1);
        newTable.setMaximumUsers(4);
        newTable.setOrientation(Orientation.East);
        newTable.setType(Furniture.Type.Table);

        inonu.getFurnitures().add(newTable);

        Furniture cart = new Furniture();
        cart.setName("meatballCart");
        cart.setPosition(new Vector3(20, 0, 0));
        cart.setRender2DDelta(new Vector2(-126, -73));
        cart.setWidth(3);
        cart.setHeight(1);
        cart.setMaximumUsers(1);
        cart.setOrientation(Orientation.West);
        cart.setType(Furniture.Type.FoodCart);

        inonu.getFurnitures().add(cart);

        inonu.incrementIngredient(Ingredient.MincedMeat, 500);

        Hawker reis = new Hawker(7, "defaultPerson");
        reis.setPosition(new Vector3(22, 0, 0));

        inonu.getEmployees().add(reis);

        return inonu;
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
        ArrayList<Recipe> returnArr = new ArrayList<Recipe>();

        for (Recipe r: getUnlockedRecipes()) {
            if (r.isAvailable())
                returnArr.add(r);
        }

        return returnArr;
    }

    public ArrayList<Recipe> getUnlockedRecipes()
    {
        ArrayList<Recipe> returnArr = new ArrayList<Recipe>();

        for (Recipe r: recipeManager.getRecipes()) {
            if (r.getMinLevel() <= level)
                returnArr.add(r);
        }

        return returnArr;
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
