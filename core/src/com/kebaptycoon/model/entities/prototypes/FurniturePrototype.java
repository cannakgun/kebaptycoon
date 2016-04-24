package com.kebaptycoon.model.entities.prototypes;

import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.Orientation;

import java.util.ArrayList;

public class FurniturePrototype {

    public String title;
    public String name;
    public String desc;
    public Furniture.Type type;
    public int width;
    public int height;
    public int maximumUsers;
    public Point renderDelta;
    public Orientation orientation;

    public FurniturePrototype() {}

    public static class Point {
        public float x;
        public float y;

        Point() {}
    }
}
