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

import java.util.ArrayList;
import java.util.Map;

import javax.swing.plaf.synth.SynthEditorPaneUI;

public class ReportsMenu extends Menu {

    private ReportManager reportManager;

    public ReportsMenu(GameScreen gameScreen) {

        super(gameScreen);
        MenuController mc = new ReportsMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        reportManager = gameScreen.getGameLogic().getReportManager();

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
        heading1Font.draw(batch, "Restoran Popülaritesi:", 500, 670);
        int x = 500;
        for(Map.Entry<CustomerType, Float> e : gameScreen.getGameLogic().getCustomerManager().getPopularities().entrySet())
        {
            heading3Font.draw(batch, e.getKey().getDisplayName() + ": " + e.getValue().toString(), x, 570);
            x += 50;
        }

        // Remaining Stocks
        ArrayList<Pair<Ingredient,Integer>> stocks = new ArrayList<Pair<Ingredient, Integer>>();
        for(Venue v : gameScreen.getGameLogic().getVenueManager().getVenueList())
        {
            for(Pair<Ingredient,Integer> p : v.getStock())
            {
                stocks.add(p);
            }
        }
        int delta = 0;
        for(Pair<Ingredient, Integer> p : gameScreen.getGameLogic().getReportManager().getDailyStockDifference(stocks))
        {
            delta += p.getRight();
        }
        heading1Font.draw(batch, "Stok Değişimi: " + delta, 500, 500);

        batch.end();
    }

}
