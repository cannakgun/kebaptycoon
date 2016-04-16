package com.kebaptycoon.model.logic;

import com.kebaptycoon.model.managers.*;

import java.util.Date;

public class GameLogic {

    private RecipeManager recipeManager;
    private Date date;
    private int money;

    public GameLogic(){

        recipeManager = new RecipeManager();

    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public int getMoney() {

        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
