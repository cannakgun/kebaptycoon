package com.kebaptycoon.model.managers;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class RecipeManager {
    private static RecipeManager ourInstance = new RecipeManager();

    public static RecipeManager getInstance() {
        return ourInstance;
    }

    private RecipeManager() {
    }
}
