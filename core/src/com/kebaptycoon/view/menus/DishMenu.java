package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.DishMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.model.logic.GameLogic;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.utils.ResourceManager;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

public class DishMenu extends Menu{

    public DishMenu(GameScreen gameScreen) {
        resourceManager = gameScreen.getResourceManager();

        heading1Generator = resourceManager.fonts.get("Boogaloo");
        heading1Parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        heading1Parameter.size = 72;
        heading1Parameter.color = Color.BLACK;

        heading1Font = heading1Generator.generateFont(heading1Parameter);
        heading1Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        heading2Generator = resourceManager.fonts.get("ClearSans");
        heading2Parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        heading2Parameter.size = 45;
        heading2Parameter.color = Color.BLACK;

        heading2Font = heading2Generator.generateFont(heading2Parameter);
        heading2Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        MenuController mc = new DishMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);
        this.gameScreen = gameScreen;

        gameLogic = gameScreen.getGameLogic();

        currentPage = 0;
    }

    public void render(SpriteBatch batch, Viewport viewPort){

        batch.begin();
        batch.draw(resourceManager.textures.get("menu_background"), 300, 300);

        heading1Font.draw(batch, Globals.DISH_MENU_TITLE, 845, 920);

        ArrayList<Recipe> recipes = gameLogic.getRecipeManager().getRecipes();
        int  min = Math.min((currentPage + 1) * 3, recipes.size());

        for (int i = currentPage * 3; i < min; i++) {
            Recipe rec = recipes.get(i);
            batch.draw(resourceManager.textures.get(rec.getTexture()), (i % 3) * 350 + 500, 550);
            heading2Font.draw(batch, rec.getName(), (i % 3) * 350 + 500, 500);

        }
        batch.end();
    }
    public void changeCurrentPage(int delta) {
        int pages = ((gameLogic.getRecipeManager().getRecipes().size() - 1) / 3) + 1;
        this.currentPage = (currentPage + pages + delta) % pages;
    }
}
