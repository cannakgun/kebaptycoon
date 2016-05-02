package com.kebaptycoon.model.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.*;
import com.kebaptycoon.model.managers.*;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.utils.ResourceManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

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
    private int day;

    public GameLogic(ResourceManager resourceManager){

        START_HOUR = new GregorianCalendar();
        START_HOUR.set(Calendar.HOUR_OF_DAY, 8);
        START_HOUR.set(Calendar.MINUTE, 0);

        END_HOUR = new GregorianCalendar();
        END_HOUR.set(Calendar.HOUR_OF_DAY, 19);
        END_HOUR.set(Calendar.MINUTE, 0);

        currentTime = new GregorianCalendar();
        currentTime.set(Calendar.HOUR_OF_DAY, 8);
        currentTime.set(Calendar.MINUTE, 0);

        level = 15;
        day = 0;

        this.resourceManager = resourceManager;
        recipeManager = new RecipeManager();
        advertisementManager = new AdvertisementManager();
        customerManager = new CustomerManager();
        marketManager = new MarketManager();
        venueManager = new VenueManager();
        animationManager = new AnimationManager(resourceManager);
        facebookFriendManager = new FacebookFriendManager();
        reportManager = new ReportManager(money);

        paused = false;
        afterHours = false;
        resetDayTime();

        venueManager.getVenueList().add(createTutorialVenue());
        venueManager.getVenueList().add(createNisantasiVenue());

        ArrayList<Pair<Ingredient,Integer>> startStocks = new ArrayList<Pair<Ingredient, Integer>>();
        for(Venue v : venueManager.getVenueList())
        {
            for(Pair<Ingredient,Integer> p : v.getStock())
            {
                Pair<Ingredient,Integer> pair = new Pair<Ingredient,Integer>(p.getLeft(), p.getRight());
                startStocks.add(pair);
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
                        animationManager.setUpAnimations(customer.getDish());
                    }
                }

                animationManager.autoSetUp(venue.getEmployees());

                for (Employee employee: venue.getEmployees()) {
                    employee.think(venue);
                }

                animationManager.autoSetUp(venue.getFurnitures());

                for (CustomerPack customerPack: venue.getCustomers()) {
                    for(Customer customer: customerPack.getCustomers()) {
                        if (customer.getDish() != null) {
                            animationManager.setUpAnimations(customer.getDish());
                        }
                    }
                }

                venue.purgeCustomers();

                EmotionManager.decayEmotions();
            }
        }
    }

    public boolean isAfterHours() {
        return currentTime.getTime().compareTo(END_HOUR.getTime()) >= 0;
    }

    private void resetDayTime() {
        currentTime.set(Calendar.HOUR_OF_DAY, START_HOUR.get(Calendar.HOUR_OF_DAY));
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

        Furniture newTable = new Furniture(marketManager.getFurnitures().get(4).getLeft());
        newTable.setPosition(new Vector3(14, -1, 0));

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(4).getLeft());
        newTable.setPosition(new Vector3(14, 2, 0));

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(4).getLeft());
        newTable.setPosition(new Vector3(14, -4, 0));

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(4).getLeft());
        newTable.setPosition(new Vector3(17, 0, 0));

        inonu.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(6).getLeft());
        newTable.setPosition(new Vector3(20, -4, 0));

        inonu.getFurnitures().add(newTable);


        newTable = new Furniture(marketManager.getFurnitures().get(6).getLeft());
        newTable.setPosition(new Vector3(20, 3, 0));

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

        inonu.incrementIngredient(Ingredient.MincedMeat, 10);
        inonu.incrementIngredient(Ingredient.Bread, 10);
        inonu.incrementIngredient(Ingredient.Egg, 10);
        inonu.incrementIngredient(Ingredient.Parsley, 10);
        inonu.incrementIngredient(Ingredient.Tomato, 10);
        inonu.incrementIngredient(Ingredient.DicedMeat, 10);
        inonu.incrementIngredient(Ingredient.DonerMeat, 10);
        inonu.incrementIngredient(Ingredient.Potato, 10);
        inonu.incrementIngredient(Ingredient.Cucumber, 10);
        inonu.incrementIngredient(Ingredient.Garlic, 10);
        inonu.incrementIngredient(Ingredient.Lavash, 10);
        inonu.incrementIngredient(Ingredient.Butter, 10);
        inonu.incrementIngredient(Ingredient.Lemon, 10);
        inonu.incrementIngredient(Ingredient.OliveOil, 10);
        inonu.incrementIngredient(Ingredient.Onion, 10);
        inonu.incrementIngredient(Ingredient.TomatoSauce, 10);
        inonu.incrementIngredient(Ingredient.PomegranateSyrup, 10);
        inonu.incrementIngredient(Ingredient.Scallion, 10);
        inonu.incrementIngredient(Ingredient.Yoghurt, 10);
        inonu.incrementIngredient(Ingredient.Pepper, 10);
        inonu.incrementIngredient(Ingredient.Lettuce, 10);
        inonu.incrementIngredient(Ingredient.Spice, 10);

        Hawker reis = new Hawker(3, "cook");
        reis.setPosition(new Vector3(22, 0, 0));

        inonu.getEmployees().add(reis);

        return inonu;
    }

    public Venue createNisantasiVenue() {

        Venue nisantasi = new Venue(30, 30, 5, 5, false,
                resourceManager.textures.get("restaurants_nisantasi"), this, new Vector3(6, -11, 0));


        Furniture newTable = new Furniture(marketManager.getFurnitures().get(5).getLeft());
        newTable.setPosition(new Vector3(14, 2, 0));

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(5).getLeft());
        newTable.setPosition(new Vector3(14, 5, 0));

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(5).getLeft());
        newTable.setPosition(new Vector3(14, -4, 0));

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(5).getLeft());
        newTable.setPosition(new Vector3(11, 2, 0));

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(5).getLeft());
        newTable.setPosition(new Vector3(11, 5, 0));

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(5).getLeft());
        newTable.setPosition(new Vector3(11, -4, 0));

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(4).getLeft());
        newTable.setPosition(new Vector3(16, -1, 0));
        newTable.setRender2DDelta(new Vector2(-61, -44));
        newTable.setName("servingTable");
        newTable.setType(Furniture.Type.ServingTable);

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(5).getLeft());
        newTable.setPosition(new Vector3(20, 2, 0));

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(5).getLeft());
        newTable.setPosition(new Vector3(20, 5, 0));

        nisantasi.getFurnitures().add(newTable);

        for (int i = 17; i < 22; i++) {
            newTable = new Furniture(marketManager.getFurnitures().get(4).getLeft());
            newTable.setPosition(new Vector3(i, -1, 0));
            newTable.setType(Furniture.Type.Blocker);

            nisantasi.getFurnitures().add(newTable);
        }

        for (int i = -4; i > -7; i--) {
            newTable = new Furniture(marketManager.getFurnitures().get(4).getLeft());
            newTable.setPosition(new Vector3(16, i, 0));
            newTable.setType(Furniture.Type.Blocker);

            nisantasi.getFurnitures().add(newTable);
        }

        for(int i = 2; i <7; i += 2){
            newTable = new Furniture(marketManager.getFurnitures().get(6).getLeft());
            newTable.setPosition(new Vector3(8, i, 0));

            nisantasi.getFurnitures().add(newTable);
        }

        for(int i = -7; i < -2; i += 2){
            newTable = new Furniture(marketManager.getFurnitures().get(6).getLeft());
            newTable.setPosition(new Vector3(8, i, 0));

            nisantasi.getFurnitures().add(newTable);
        }

        newTable = new Furniture(marketManager.getFurnitures().get(0).getLeft());
        newTable.setPosition(new Vector3(21, -2, 0));

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(3).getLeft());
        newTable.setPosition(new Vector3(21, -6, 0));
        newTable.setType(Furniture.Type.Grill);

        nisantasi.getFurnitures().add(newTable);

        newTable = new Furniture(marketManager.getFurnitures().get(2).getLeft());
        newTable.setPosition(new Vector3(21, -4, 0));
        newTable.setType(Furniture.Type.Cooker);

        nisantasi.getFurnitures().add(newTable);

        nisantasi.incrementIngredient(Ingredient.MincedMeat, 10);
        nisantasi.incrementIngredient(Ingredient.Bread, 10);
        nisantasi.incrementIngredient(Ingredient.Egg, 10);
        nisantasi.incrementIngredient(Ingredient.Parsley, 10);
        nisantasi.incrementIngredient(Ingredient.Tomato, 10);
        nisantasi.incrementIngredient(Ingredient.DicedMeat, 10);
        nisantasi.incrementIngredient(Ingredient.DonerMeat, 10);
        nisantasi.incrementIngredient(Ingredient.Potato, 10);
        nisantasi.incrementIngredient(Ingredient.Cucumber, 10);
        nisantasi.incrementIngredient(Ingredient.Garlic, 10);
        nisantasi.incrementIngredient(Ingredient.Lavash, 10);
        nisantasi.incrementIngredient(Ingredient.Butter, 10);
        nisantasi.incrementIngredient(Ingredient.Lemon, 10);
        nisantasi.incrementIngredient(Ingredient.OliveOil, 10);
        nisantasi.incrementIngredient(Ingredient.Onion, 10);
        nisantasi.incrementIngredient(Ingredient.TomatoSauce, 10);
        nisantasi.incrementIngredient(Ingredient.PomegranateSyrup, 10);
        nisantasi.incrementIngredient(Ingredient.Scallion, 10);
        nisantasi.incrementIngredient(Ingredient.Yoghurt, 10);
        nisantasi.incrementIngredient(Ingredient.Pepper, 10);
        nisantasi.incrementIngredient(Ingredient.Lettuce, 10);
        nisantasi.incrementIngredient(Ingredient.Spice, 10);

        Employee reis = new Cook(3, "cook");
        reis.setPosition(new Vector3(20, -2, 0));
        reis.setOrientation(Orientation.West);

        nisantasi.getEmployees().add(reis);

        reis = new Waiter(3, "waiter");
        reis.setPosition(new Vector3(18, -2, 0));
        reis.setOrientation(Orientation.West);

        nisantasi.getEmployees().add(reis);

        return nisantasi;

    }

    public int getMoney() {

        return money;
    }

    private void OnDayStart() {
        reportManager.setStartMoney(money);
        ArrayList<Pair<Ingredient,Integer>> stocks = new ArrayList<Pair<Ingredient, Integer>>();
        for(Venue v : venueManager.getVenueList())
        {
            for(Pair<Ingredient,Integer> p : v.getStock())
            {
                Pair<Ingredient,Integer> pair = new Pair<Ingredient,Integer>(p.getLeft(), p.getRight());
                stocks.add(pair);
            }
        }

        reportManager.setStartStock(stocks);

        reportManager.resetDailyOrders();

        day++;
    }


    private void OnDayEnd() {
        EmotionManager.flushEmotions();
        customerManager.decayPopularity();
        processAdvertisements();

        HashMap<String, String> reportDetails = new HashMap<String, String>();
        reportDetails.put("venue_count",
                ""+getVenueManager().getVenueList().size());
        reportDetails.put("level", ""+getLevel());
        reportDetails.put("daily_income", "" + reportManager.getDailyMoneyDifference(getMoney()));
        try {
            reportManager.sendReportDetailsToServer(reportDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        for (Recipe r : getUnlockedRecipes()) {
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

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(currentTime.getTime());
        return date;
    }

    public ReportManager getReportManager()
    {
        return reportManager;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public Venue resetVenue(int index){

        resetDayTime();
        for(Venue v : venueManager.getVenueList()) {
            v.getOrderManager().resetOrders();
            v.getCustomers().clear();
            for(int i = 0; i < v.getEmployees().size(); i++){
                v.getEmployees().get(i).reset();
            }
            v.getTableManager().reset();
            for(Furniture f: v.getFurnitures()) {
                f.clearUsers();
            }
        }

        return venueManager.getVenueList().get(index);
    }
    public int getDay(){
        return day;
    }

    public int getLevel(){ return level; }
}
