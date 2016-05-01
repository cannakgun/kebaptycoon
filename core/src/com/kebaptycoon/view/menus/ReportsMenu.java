package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.controller.menuControllers.ReportsMenuController;
import com.kebaptycoon.model.entities.CustomerType;
import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.entities.Venue;
import com.kebaptycoon.model.managers.ReportManager;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.view.screens.GameScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.plaf.synth.SynthEditorPaneUI;

public class ReportsMenu extends Menu {

    private ReportManager reportManager;
    private boolean isLoaded;
    private Pair<Integer, Integer> pair;

    public ReportsMenu(GameScreen gameScreen) {

        super(gameScreen);
        MenuController mc = new ReportsMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        reportManager = gameScreen.getGameLogic().getReportManager();
        isLoaded = false;
        pair = null;

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        currentPage = 0;

    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {
        batch.begin();

        batch.draw(resourceManager.textures.get("menu_backgrounds_reportsBackground"), 300, 300);

        // Daily Income
        heading1Font.draw(batch, "Günlük Gelir: " + reportManager.getDailyMoneyDifference(gameScreen.getGameLogic().getMoney()) + " TL", 500, 800);

        // Popularity among customer types
        heading1Font.draw(batch, "Popülarite:", 500, 670);
        int x = 500;
        for(Map.Entry<CustomerType, Float> e : gameScreen.getGameLogic().getCustomerManager().getPopularities().entrySet())
        {
            batch.draw(resourceManager.textures.get("advertisements_" + e.getKey().getTextureName()), x, 540, 50, 50);
            heading3Font.draw(batch, e.getValue().toString(), x, 520);
            x += 100;
        }

        // Remaining Stocks
        if(!isLoaded)
        {
            pair = reportManager.getRemainingStocks(gameScreen.getGameLogic()
                    .getVenueManager().getVenueList(), gameScreen.getGameLogic()
                                                    .getMarketManager().getIngredients());
            isLoaded = true;
        }

        heading1Font.draw(batch, "Stok Kullanımı: " + pair.getRight() + "TL", 500, 450);

        batch.end();
    }
}
