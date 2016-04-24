package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.DishDetailsMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

public class DishDetailsMenu extends Menu {

    private int dishIndex;
    private Recipe cloneRecipe;
    private Recipe originalRecipe;
    private ArrayList<Pair<Ingredient, Integer>> cloneRecipeIngredients;

    public DishDetailsMenu(GameScreen gameScreen, int dishIndex) {

        super(gameScreen);
        this.dishIndex = dishIndex;

        originalRecipe = gameScreen.getGameLogic().getRecipeManager().getRecipes().get(dishIndex);

        cloneRecipeIngredients = new ArrayList<Pair<Ingredient, Integer>>();

        for(int i = 0; i < originalRecipe.getIngredients().size(); i++) {
            Pair p = new Pair<Ingredient, Integer>();
            p.left = originalRecipe.getIngredients().get(i).getLeft();
            p.right = originalRecipe.getIngredients().get(i).getRight();
            cloneRecipeIngredients.add(p);
        }

        cloneRecipe = new Recipe(originalRecipe.getName(), cloneRecipeIngredients, originalRecipe.getPrice(),
                originalRecipe.getProcess(), originalRecipe.getMinLevel(), originalRecipe.isAvailable(),
                originalRecipe.getTexture());

        MenuController mc = new DishDetailsMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);
    }

    public void render(SpriteBatch batch, Viewport viewPort){

        batch.begin();
        batch.draw(resourceManager.textures.get("menu_backgrounds_menuBackground"), 300, 300);

        heading1Font.draw(batch, cloneRecipe.getName()
                , 805, 920);

        batch.draw(resourceManager.textures.get(cloneRecipe.getTexture()), 460, 520);

        batch.draw(resourceManager.textures.get("menu_approve"), 810 , 320);
        batch.draw(resourceManager.textures.get("menu_back"), 450 , 320);

        heading2Font.draw(batch, "" + cloneRecipe.getPrice(), 510, 490);
        batch.draw(resourceManager.textures.get("menu_gold"), 585, 455, 45, 45);
        batch.draw(resourceManager.textures.get("menu_minus"), 460  , 460);
        batch.draw(resourceManager.textures.get("menu_plus"),  650 , 460);

        int y = 900;

        for (int i = 0; i < cloneRecipe.getIngredients().size(); i++){
            if(i%3 == 0)
                y -= 200;

            batch.draw(resourceManager.textures.get("ingredients_" + cloneRecipe.getIngredients().get(i).getLeft()),
                    850 + (i%3) * 240, y, 100, 100);
            batch.draw(resourceManager.textures.get("menu_minus"), 840 + (i%3) * 240, y - 45);
            batch.draw(resourceManager.textures.get("menu_plus"), 940 + (i%3) * 240, y - 45);
            heading3Font.draw(batch, ""+cloneRecipe.getIngredients().get(i).getRight(), 900 + (i%3) * 240, y - 25);
        }
        batch.end();
    }

    public int getDishIndex() {
        return dishIndex;
    }

    public Recipe getCloneRecipe() {
        return cloneRecipe;
    }

    public Recipe getOriginalRecipe() {
        return originalRecipe;
    }

    public void setOriginalRecipe() {

        ArrayList<Pair<Ingredient, Integer>> originalRecipeIngredients = new ArrayList<Pair<Ingredient, Integer>>();

        for(int i = 0; i < originalRecipe.getIngredients().size(); i++) {
            Pair p = new Pair<Ingredient, Integer>();
            p.left = cloneRecipe.getIngredients().get(i).getLeft();
            p.right = cloneRecipe.getIngredients().get(i).getRight();
            originalRecipeIngredients.add(p);
        }

        originalRecipe.setIngredients(originalRecipeIngredients);
        originalRecipe.setPrice(cloneRecipe.getPrice());
        originalRecipe = cloneRecipe;
    }
}
