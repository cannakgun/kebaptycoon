package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.DishMenuController;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.model.logic.GameLogic;
import com.kebaptycoon.utils.ResourceManager;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by Can Akgün on 7.4.2016.
 */
public class DishMenu extends Menu{

    private GameLogic gameLogic;
    private GameScreen gameScreen;
    private ResourceManager resourceManager;

    public DishMenu(GameScreen gameScreen) {
        super.menuController = new DishMenuController(gameScreen);
        super.shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(new GestureDetector(menuController));
        this.gameScreen = gameScreen;
        resourceManager = gameScreen.getResourceManager();
        gameLogic = gameScreen.getGameLogic();

    }

    public void render(SpriteBatch batch, Viewport viewPort){

        batch.begin();
        //batch.draw(ResourceManager.getInstance().badLogic,100,100,500,500);
        for (Recipe rec:gameLogic.getRecipeManager().getRecipes()) {
            batch.draw(resourceManager.textures.get(rec.getTexture()), 100, 100);
        }
            //batch.draw(gameLogic.getRecipeManager().);
        batch.end();
    }

}
