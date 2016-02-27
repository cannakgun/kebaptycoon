package com.kebaptycoon.model.managers;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class CustomerManager {
    private static CustomerManager ourInstance = new CustomerManager();

    public static CustomerManager getInstance() {
        return ourInstance;
    }

    private CustomerManager() {
    }
}
