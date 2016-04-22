package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.CustomerPack;
import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.Venue;

import java.util.ArrayList;
import java.util.HashMap;

public class TableManager {

    private Venue venue;
    private HashMap<CustomerPack, Furniture> assignments;

    public TableManager(Venue venue) {
        this.venue = venue;
        assignments = new HashMap<CustomerPack, Furniture>();
    }

    public ArrayList<Furniture> getTableList()
    {
        return venue.getFurnitures(Furniture.Type.Table);
    }

    public Furniture getTableFor(CustomerPack pack)
    {
        //If the pack is not assigned and it can fit the pack into an available table
        //Assign them to the table and mark it occupied

        //If the pack is assigned but cannot be assigned return null

        //If the pack is already assigned return the assigned table

        if(assignments.containsKey(pack))
        {
            return assignments.get(pack);
        }
        else
        {
            Furniture availableTable = getTable(pack.getCustomers().size());

            if(availableTable != null)
            {
                assignments.put(pack, availableTable);
                return availableTable;
            }
        }

        return null;
    }

    private Furniture getTable(int packSize)
    {
        for (Furniture table : getTableList()) {
            if (assignments.containsValue(table))
                continue;

            if (table.getMaximumUsers() >= packSize)
                return table;
        }

        return null;
    }

    public void leaveTable(CustomerPack pack)
    {
        //If the given pack is assigned to a table, mark that table free
        assignments.remove(pack);
    }
}