package com.kebaptycoon.model.entities.prototypes;

import com.kebaptycoon.model.entities.Furniture;

import java.util.ArrayList;

public class FurniturePrototype {

    public String name;
    public String desc;
    public Furniture.Type type;
    public int width;
    public int height;
    public int maximumUsers;
    public ArrayList<Point> userPositions;

    public FurniturePrototype() {}

    public static class Point {
        public float x;
        public float y;

        Point() {}
    }
}
