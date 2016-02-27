package com.kebaptycoon.model.managers;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class TableManager {
    private static TableManager ourInstance = new TableManager();

    public static TableManager getInstance() {
        return ourInstance;
    }

    private TableManager() {
    }
}
