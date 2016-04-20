package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.Venue;

import java.util.ArrayList;
import java.util.function.Predicate;

public class TableManager {
    private Venue venue;

    public TableManager(Venue venue) {
        this.venue = venue;
    }

    public ArrayList<Furniture> getTableList() {
        ArrayList<Furniture> r = new ArrayList<Furniture>(venue.getFurnitures());
        r.removeIf(new Predicate<Furniture>() {
            @Override
            public boolean test(Furniture f) {
                return f.getType() != Furniture.Type.Table;
            }
        });
        return r;
    }
}
