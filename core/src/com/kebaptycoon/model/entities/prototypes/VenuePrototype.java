package com.kebaptycoon.model.entities.prototypes;

import java.util.ArrayList;

public class VenuePrototype {

    public int height;
    public int kitchenWidth;
    public int kitchenHeight;
    public boolean operational;
    public float spawnX;
    public float spawnY;
    public String background;
    public ArrayList<Blocker> blockers;

    public VenuePrototype() {}

    public static class Blocker {
        public float x;
        public float y;
        public float w;
        public float h;

        Blocker() {}
    }
}
