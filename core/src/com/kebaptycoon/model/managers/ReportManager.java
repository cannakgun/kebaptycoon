package com.kebaptycoon.model.managers;

import com.kebaptycoon.KebapTycoonGame;
import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.entities.Order;
import com.kebaptycoon.model.entities.Venue;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.utils.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportManager {
    private int startMoney;
    private ArrayList<Pair<Ingredient,Integer>> startStock;
    private ArrayList<Order> dailyOrders;

    public ReportManager(){
        startMoney = 0;
        startStock = new ArrayList<Pair<Ingredient, Integer>>();
        dailyOrders = new ArrayList<Order>();
    }

    public ReportManager(int startMoney)
    {
        this.startMoney = startMoney;
        startStock = new ArrayList<Pair<Ingredient, Integer>>();
        dailyOrders = new ArrayList<Order>();
    }

    public void setStartMoney(int startMoney)
    {
        this.startMoney = startMoney;
    }

    public int getStartMoney()
    {
        return startMoney;
    }

    public ArrayList<Pair<Ingredient,Integer>> getStartStock()
    {
        return startStock;
    }

    public void setStartStock(ArrayList<Pair<Ingredient,Integer>> startStock)
    {
        this.startStock = startStock;
    }

    public int getDailyMoneyDifference(int money)
    {
        return money - startMoney;
    }

    public ArrayList<Pair<Ingredient,Integer>> getDailyStockDifference(ArrayList<Pair
            <Ingredient,Integer>> stock)
    {
        ArrayList<Pair<Ingredient,Integer>> diff = new ArrayList<Pair<Ingredient,Integer>>();

        for(int i = 0; i < startStock.size(); i++)
        {
            diff.add(new Pair(startStock.get(i).getLeft(),
                    startStock.get(i).getRight() - stock.get(i).getRight()));
        }

        return diff;
    }

    public ArrayList<Order> getDailyOrders()
    {
        return dailyOrders;
    }

    public void addDailyOrder(Order o)
    {
        dailyOrders.add(o);
    }

    public void resetDailyOrders()
    {
        dailyOrders.clear();
    }

    public Pair<Integer, Integer> getRemainingStocks(ArrayList<Venue> venueList,
                                                     ArrayList<Pair<Ingredient, Integer>> ingredients)
    {
        int delta = 0;
        int price = 0;

        ArrayList<Pair<Ingredient,Integer>> stocks = new ArrayList<Pair<Ingredient, Integer>>();
        for(Venue v : venueList)
        {
            for(Pair<Ingredient, Integer> p : v.getStock())
            {
                stocks.add(p);
            }
        }

        for(Pair<Ingredient, Integer> p : getDailyStockDifference(stocks))
        {
            delta += p.getRight();

            for(Pair<Ingredient, Integer> pair : ingredients)
            {
                if(pair.getLeft().equals(p.getLeft()))
                    price += (p.getRight() * pair.getRight());
            }
        }

        return new Pair(delta, price);
    }

    public void sendReportDetailsToServer(HashMap <String, String> reportDetails) throws IOException {
        JSONObject reportJSON = new JSONObject();
        for (Map.Entry<String, String> entry : reportDetails.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                reportJSON.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String url = Globals.SERVER_API_URL + "insert_player_stats.php?";
        url += "fb_user_id=" + KebapTycoonGame.getInstance().getPrefs().getString("facebook_user_id") +
                "&player_stat=" + reportJSON.toString();

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}
