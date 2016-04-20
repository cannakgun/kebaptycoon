package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.DishMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.controller.menuControllers.StockMenuController;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.view.screens.GameScreen;

public class StockMenu extends Menu {
    public StockMenu(GameScreen gameScreen) {

        MenuController mc = new StockMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);
        this.gameScreen = gameScreen;

        gameLogic = gameScreen.getGameLogic();

        currentPage = 0;
    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {

        batch.begin();
        batch.draw(resourceManager.textures.get("menu_background"), 300, 300);

        heading1Font.draw(batch, Globals.STOCK_MENU_TITLE, 845, 920);
        batch.end();

    }

}
