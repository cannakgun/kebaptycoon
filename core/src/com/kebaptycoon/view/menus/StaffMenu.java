package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.MarketMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.controller.menuControllers.StaffMenuController;
import com.kebaptycoon.model.entities.prototypes.EmployeePrototype;
import com.kebaptycoon.model.entities.prototypes.FurniturePrototype;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

public class StaffMenu extends Menu{

    private ArrayList<Pair<EmployeePrototype, Integer>> staffList;

    public StaffMenu(GameScreen gameScreen) {

        super(gameScreen);
        MenuController mc = new StaffMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        currentPage = 0;

        staffList = gameScreen.getGameLogic().getMarketManager().getEmployees();

    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {

        batch.begin();
        batch.draw(resourceManager.textures.get("menu_background"), 300, 300);

        heading1Font.draw(batch, Globals.STAFF_MENU_TITLE, 845, 920);

        int y = 920;

        int  min = Math.min((currentPage + 1) * 8, staffList.size());

        for (int i = currentPage * 8; i < min; i++) {

            Pair staff = staffList.get(i);
            EmployeePrototype emp = (EmployeePrototype) staff.getLeft();

            if(i%4 == 0)
                y -= 240;

            batch.draw(resourceManager.textures.get("staff_" + emp.sprite),
                    500 + (i%4) * 240, y, 100, 100);
            heading3Font.draw(batch, staff.getRight() + " TL ", 500 + (i%4) * 240 + 30, y - 30);

        }
        batch.end();
    }

    public void changeCurrentPage(int delta) {
        int pages = ((staffList.size() - 1) / 8) + 1;
        this.currentPage = (currentPage + pages + delta) % pages;
    }
}
