package com.kebaptycoon.model.managers;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.entities.prototypes.EmployeePrototype;
import com.kebaptycoon.model.entities.prototypes.FurniturePrototype;
import com.kebaptycoon.model.entities.prototypes.VenuePrototype;
import com.kebaptycoon.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class MarketManager {

    private ArrayList<Pair<FurniturePrototype, Integer>>  furnitures;
    private ArrayList<Pair<VenuePrototype, Integer>>  venues;
    private ArrayList<Pair<Ingredient, Integer>> ingredients;
    private ArrayList<Pair<EmployeePrototype, Integer>> staff;

    public MarketManager() {

        furnitures = new ArrayList<Pair<FurniturePrototype, Integer>>();
        venues = new ArrayList<Pair<VenuePrototype, Integer>>();
        ingredients = new ArrayList<Pair<Ingredient, Integer>>();
        staff = new ArrayList<Pair<EmployeePrototype, Integer>>();

        ObjectMapper mapper = new ObjectMapper();

        try {

            String furnitureJSON = Gdx.files.internal("defaults/FurnitureList.json").readString();

            furnitures = mapper.readValue(furnitureJSON,
                    new TypeReference<ArrayList<Pair<FurniturePrototype, Integer>>>() {});

            String venueJSON = Gdx.files.internal("defaults/VenueList.json").readString();

            venues = mapper.readValue(venueJSON,
                    new TypeReference<ArrayList<Pair<VenuePrototype, Integer>>>() {});

            String ingredientJSON = Gdx.files.internal("defaults/IngredientList.json").readString();

            ingredients = mapper.readValue(ingredientJSON,
                    new TypeReference<ArrayList<Pair<Ingredient, Integer>>>() {});

            String staffJSON = Gdx.files.internal("defaults/StaffList.json").readString();

            staff = mapper.readValue(staffJSON,
                    new TypeReference<ArrayList<Pair<EmployeePrototype, Integer>>>() {});


        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pair<FurniturePrototype, Integer>> getFurnitures() {
        return furnitures;
    }

    public ArrayList<Pair<VenuePrototype, Integer>> getVenues() {
        return venues;
    }

    public ArrayList<Pair<Ingredient, Integer>> getIngredients() {
        return ingredients;
    }

    public ArrayList<Pair<EmployeePrototype, Integer>> getEmployees() {
        return staff;
    }
}
