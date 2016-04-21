package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.CustomerPack;
import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.Venue;

import java.util.ArrayList;

public class TableManager {

    private Venue venue;

    public TableManager(Venue venue) {
        this.venue = venue;
    }

    public ArrayList<Furniture> getTableList() {
        return venue.getFurnitures(Furniture.Type.Table);
    }

    public Furniture getTableFor(CustomerPack pack) {
        //If the pack is not assigned and it can fit the pack into an available table
        //Assign them to the table and mark it occupied

        //If the pack is assigned but cannot be assigned return null

        //If the pack is already assigned return the assigned table
        return null;
    }

    public void leaveTable(CustomerPack pack) {
        //If the given pack is assigned to a table, mark that table free
    }
}
