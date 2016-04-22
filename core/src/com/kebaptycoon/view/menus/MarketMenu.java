package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.MarketMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.controller.menuControllers.StockMenuController;
import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.entities.prototypes.FurniturePrototype;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

public class MarketMenu extends Menu {


    private ArrayList<Pair<FurniturePrototype, Integer>> furnitures;

    public MarketMenu(GameScreen gameScreen) {

        super(gameScreen);
        MenuController mc = new MarketMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        currentPage = 0;

        furnitures = gameScreen.getGameLogic().getMarketManager().getFurnitures();

    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {

        batch.begin();
        batch.draw(resourceManager.textures.get("menu_background"), 300, 300);

        heading1Font.draw(batch, Globals.MARKET_MENU_TITLE, 825, 920);

        int y = 920;

        int  min = Math.min((currentPage + 1) * 8, furnitures.size());

        for (int i = currentPage * 8; i < min; i++) {

            Pair furniture = furnitures.get(i);
            FurniturePrototype pro = (FurniturePrototype) furniture.getLeft();
            if(i%4 == 0)
                y -= 240;
            batch.draw(resourceManager.textures.get("furnitures_" + pro.name),
                    520 + (i%4) * 240, y, 100, 100);
            heading3Font.draw(batch, furniture.getRight() + " TL ", 500 + (i%4) * 240 + 30, y - 30);

        }
        batch.end();
    }

    public void changeCurrentPage(int delta) {
        int pages = ((furnitures.size() - 1) / 8) + 1;
        this.currentPage = (currentPage + pages + delta) % pages;
    }
}
