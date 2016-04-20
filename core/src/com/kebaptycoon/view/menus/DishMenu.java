package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.DishMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

public class DishMenu extends Menu{

    public DishMenu(GameScreen gameScreen) {

        super(gameScreen);

        MenuController mc = new DishMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        currentPage = 0;
    }

    public void render(SpriteBatch batch, Viewport viewPort){

        batch.begin();
        batch.draw(resourceManager.textures.get("menu_background"), 300, 300);

        heading1Font.draw(batch, Globals.DISH_MENU_TITLE, 845, 920);

        ArrayList<Recipe> recipes = gameLogic.getAvailableRecipes();//getRecipeManager().getRecipes();
        int  min = Math.min((currentPage + 1) * 3, recipes.size());

        for (int i = currentPage * 3; i < min; i++) {
            Recipe rec = recipes.get(i);
            batch.draw(resourceManager.textures.get(rec.getTexture()), (i % 3) * 350 + 500, 550);
            heading2Font.draw(batch, rec.getName(), (i % 3) * 350 + 500, 500);

        }
        batch.end();
    }
    public void changeCurrentPage(int delta) {
        int pages = ((gameLogic.getAvailableRecipes().size() - 1) / 3) + 1;
        this.currentPage = (currentPage + pages + delta) % pages;
    }
}
