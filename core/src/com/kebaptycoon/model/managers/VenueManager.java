package com.kebaptycoon.model.managers;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class VenueManager {
    private static VenueManager ourInstance = new VenueManager();

    public static VenueManager getInstance() {
        return ourInstance;
    }

    private VenueManager() {
    }
}
