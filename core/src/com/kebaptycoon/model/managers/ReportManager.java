package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.entities.Order;
import com.kebaptycoon.utils.Pair;

import java.util.ArrayList;

public class ReportManager {
    private int startMoney;
    private ArrayList<Pair<Ingredient,Integer>> startStock;
    private ArrayList<Order> dailyOrders;

    public ReportManager(){
        startMoney = 0;
        startStock = new ArrayList<Pair<Ingredient, Integer>>();
        dailyOrders = new ArrayList<Order>();
    }

    public ReportManager(int startMoney, ArrayList<Pair<Ingredient,Integer>> startStock)
    {
        this.startMoney = startMoney;
        this.startStock = startStock;
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

    public ArrayList<Pair<Ingredient,Integer>> getDailyStockDifference(ArrayList<Pair<Ingredient,Integer>> stock)
    {
        ArrayList<Pair<Ingredient,Integer>> diff = new ArrayList<Pair<Ingredient,Integer>>();

        for(int i = 0; i < stock.size(); i++)
        {
            diff.add(new Pair(startStock.get(i).getLeft(), startStock.get(i).getRight() - stock.get(i).getRight()));
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
}
