package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.AdvertisementMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.entities.Advertisement;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

public class AdvertisementMenu extends Menu {

    private ArrayList<Advertisement> advertisementList;

    public AdvertisementMenu(GameScreen gameScreen) {

        super(gameScreen);
        MenuController mc = new AdvertisementMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        currentPage = 0;

        advertisementList = gameScreen.getGameLogic().getAdvertisementManager().getAdvertisementList();
    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {
        batch.begin();
        batch.draw(resourceManager.textures.get("menu_backgrounds_advertisementBackground"), 300, 300);

        int y = 920;


        for (int i = currentPage * 8; i < 3; i++) {

            if (i % 3 == 0)
                y -= 340;
            if(i % 2 == 0)
            {
                batch.draw(resourceManager.textures.get("advertisements_tv"),
                        540 + (i % 4) * 340, y, 125, 125);
                heading3Font.draw(batch, "3000 TL ", 540 + (i % 4) * 340 + 30, y - 30);
            }
            else
            {
                batch.draw(resourceManager.textures.get("advertisements_newspaper"),
                        540 + (i % 4) * 340, y, 125, 125);
                heading3Font.draw(batch, "1000 TL ", 540 + (i % 4) * 340 + 30, y - 30);
            }


            //heading1Font.draw(batch, Globals.ADVERTISEMENT_MENU_TITLE, 815, 920);

        }
        batch.end();
    }

}
