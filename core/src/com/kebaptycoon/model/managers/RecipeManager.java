package com.kebaptycoon.model.managers;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeManager {

    ArrayList<Recipe> recipes;

    public RecipeManager() {

        recipes = new ArrayList<Recipe>();

        ObjectMapper mapper = new ObjectMapper();

        try {

            String recipeJSON = Gdx.files.internal("defaults/RecipeList.json").readString();

            recipes = mapper.readValue(recipeJSON,
                    new TypeReference<ArrayList<Recipe>>() {});

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RecipeManager(String loadData)
    {

        recipes = new ArrayList<Recipe>();

        ObjectMapper mapper = new ObjectMapper();

        try {

            recipes = mapper.readValue(loadData,
                    new TypeReference<ArrayList<Recipe>>() {});

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public String jsonString() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(recipes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
