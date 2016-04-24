package com.kebaptycoon.model.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.*;
import com.kebaptycoon.model.managers.*;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.utils.ResourceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GameLogic {

    public static Calendar START_HOUR;
    public static Calendar END_HOUR;
    public static final int SECONDS_PER_FRAME = 30;

    //Managers
    private ResourceManager resourceManager;
    private RecipeManager recipeManager;
    private AdvertisementManager advertisementManager;
    private CustomerManager customerManager;
    private MarketManager marketManager;
    private VenueManager venueManager;
    private AnimationManager animationManager;
    private FacebookFriendManager facebookFriendManager;
    private ReportManager reportManager;

    //Global game data
    private int money;
    private int level;

    //Vars
    private boolean paused;
    private boolean afterHours;
    private Calendar currentTime;

    public GameLogic(ResourceManager resourceManager){

        START_HOUR = new GregorianCalendar();
        START_HOUR.set(Calendar.HOUR, 8);
        START_HOUR.set(Calendar.MINUTE, 0);

        END_HOUR = new GregorianCalendar();
        END_HOUR.set(Calendar.HOUR, 18);
        END_HOUR.set(Calendar.MINUTE, 0);

        currentTime = new GregorianCalendar();
        currentTime.set(Calendar.HOUR, 8);
        currentTime.set(Calendar.MINUTE, 0);

        level = 15;

        this.resourceManager = resourceManager;
        recipeManager = new RecipeManager();
        advertisementManager = new AdvertisementManager();
        customerManager = new CustomerManager();
        marketManager = new MarketManager();
        venueManager = new VenueManager();
        animationManager = new AnimationManager(resourceManager);
        facebookFriendManager = new FacebookFriendManager();
        reportManager = new ReportManager(money, null);

        paused = false;
        afterHours = false;
        resetDayTime();

        venueManager.getVenueList().add(createTutorialVenue());

        ArrayList<Pair<Ingredient,Integer>> startStocks = new ArrayList<Pair<Ingredient, Integer>>();
        for(Venue v : venueManager.getVenueList())
        {
            for(Pair<Ingredient,Integer> p : v.getStock())
            {
                startStocks.add(p);
            }
        }

        reportManager.setStartStock(startStocks);
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

            currentTime.add(Calendar.SECOND, SECONDS_PER_FRAME);

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

    public boolean isAfterHours() {
        return currentTime.getTime().compareTo(END_HOUR.getTime()) > 0;
    }

    private void resetDayTime() {
        currentTime.set(Calendar.HOUR, 8);
        currentTime.set(Calendar.MINUTE, 0);
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
        inonu.incrementIngredient(Ingredient.Bread, 500);
        inonu.incrementIngredient(Ingredient.Egg, 500);
        inonu.incrementIngredient(Ingredient.Parsley, 500);
        inonu.incrementIngredient(Ingredient.Tomato, 500);
        inonu.incrementIngredient(Ingredient.Spice, 500);

        Hawker reis = new Hawker(3, "defaultPerson");
        reis.setPosition(new Vector3(22, 0, 0));

        inonu.getEmployees().add(reis);

        return inonu;
    }

    public int getMoney() {

        return money;
    }

    private void OnDayStart() {
        reportManager.setStartMoney(money);
        System.out.println("STARTMONEY: " + reportManager.getStartMoney());

        ArrayList<Pair<Ingredient,Integer>> stocks = new ArrayList<Pair<Ingredient, Integer>>();
        for(Venue v : venueManager.getVenueList())
        {
            for(Pair<Ingredient,Integer> p : v.getStock())
            {
                stocks.add(p);
            }
        }

        reportManager.setStartStock(stocks);
        System.out.println("STARTSTOCK: " + reportManager.getStartStock());

        reportManager.resetDailyOrders();
    }

    private void OnDayEnd() {
        System.out.println("ENDMONEY: " + reportManager.getDailyMoneyDifference(money));

        ArrayList<Pair<Ingredient,Integer>> stocks = new ArrayList<Pair<Ingredient, Integer>>();
        for(Venue v : venueManager.getVenueList())
        {
            for(Pair<Ingredient,Integer> p : v.getStock())
            {
                stocks.add(p);
            }
        }
        System.out.println("ENDSTOCK: " + reportManager.getDailyStockDifference(stocks));

        System.out.println("ENDORDERS: " + reportManager.getDailyOrders());

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
        return paused || afterHours;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public String getTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        String date = sdf.format(currentTime.getTime());
        return date;
    }

    public ReportManager getReportManager()
    {
        return reportManager;
    }
}
