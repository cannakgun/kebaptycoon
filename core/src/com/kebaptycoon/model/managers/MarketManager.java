package com.kebaptycoon.model.managers;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class MarketManager {

    private ArrayList<Pair<Furniture, Integer>>  furnitures;
    private ArrayList<Pair<Ingredient, Integer>> ingredients;

    public MarketManager() {

        furnitures = new ArrayList<Pair<Furniture, Integer>>();
        ingredients = new ArrayList<Pair<Ingredient, Integer>>();

        ObjectMapper mapper = new ObjectMapper();

        try {

            String furnitureJSON = Gdx.files.internal("defaults/FurnitureList.json").readString();

            furnitures = mapper.readValue(furnitureJSON,
                    new TypeReference<ArrayList<Pair<Furniture, Integer>>>() {});

            String ingredientJSON = Gdx.files.internal("defaults/IngredientList.json").readString();

            ingredients = mapper.readValue(ingredientJSON,
                    new TypeReference<ArrayList<Pair<Furniture, Integer>>>() {});


        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pair<Furniture, Integer>> getFurnitures() {
        return furnitures;
    }

    public ArrayList<Pair<Ingredient, Integer>> getIngredients() {
        return ingredients;
    }
}
