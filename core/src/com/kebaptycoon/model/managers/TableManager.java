package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Customer;
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
        return venue.getFurnitures(Furniture.Type.Table);
    }

    public Furniture getTableOf(Customer customer) {
        return null;
    }
}
