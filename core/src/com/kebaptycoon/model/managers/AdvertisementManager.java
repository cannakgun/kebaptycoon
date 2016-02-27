package com.kebaptycoon.model.managers;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class AdvertisementManager {
    private static AdvertisementManager ourInstance = new AdvertisementManager();

    public static AdvertisementManager getInstance() {
        return ourInstance;
    }

    private AdvertisementManager() {
    }
}
