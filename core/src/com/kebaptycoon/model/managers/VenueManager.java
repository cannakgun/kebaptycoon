package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Venue;

import java.util.ArrayList;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class VenueManager {

    private ArrayList<Venue> venueList;

    public VenueManager() {
        venueList = new ArrayList<Venue>();
    }

    public ArrayList<Venue> getVenueList() {
        return venueList;
    }
}
