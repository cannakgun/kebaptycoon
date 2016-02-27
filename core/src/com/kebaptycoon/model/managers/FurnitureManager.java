package com.kebaptycoon.model.managers;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class FurnitureManager {
    private static FurnitureManager ourInstance = new FurnitureManager();

    public static FurnitureManager getInstance() {
        return ourInstance;
    }

    private FurnitureManager() {
    }
}
