package com.kebaptycoon.model.logic;

import com.kebaptycoon.model.managers.*;

public class GameLogic {

    RecipeManager recipeManager;

    public GameLogic(){

        recipeManager = new RecipeManager();

    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }
}
