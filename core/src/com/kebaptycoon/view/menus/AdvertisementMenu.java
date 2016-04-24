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

        advertisementList = gameScreen.getGameLogic().getAdvertisementManager().getAdvertisementList();
    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {
        batch.begin();
        batch.draw(resourceManager.textures.get("menu_backgrounds_advertisementBackground"), 300, 300);

        int y = 900;

        for (int i = 0; i < 3; i++) {

            y -= 160;

            if (i == 2) {
                batch.draw(resourceManager.textures.get("advertisements_newspaper"),
                        500, y, 70, 70);
                heading3Font.draw(batch, "1000 TL", 500, y - 15);
            } else if (i == 1) {
                batch.draw(resourceManager.textures.get("advertisements_tv"),
                        500, y, 70, 70);
                heading3Font.draw(batch, "4000 TL", 500, y - 15);
            } else {
                batch.draw(resourceManager.textures.get("advertisements_tv"),
                        500, y, 70, 70);
                heading3Font.draw(batch, "2000 TL", 500, y - 15);
            }

        }
        batch.draw(resourceManager.textures.get("menu_lineShort"), 670, 450);

        y = 870;

        for (int i = 0; i < 3; i++) {

            y -= 120;

            if (i == 2) {

                heading3Font.draw(batch, "Kaliteli", 770, y);
            } else if (i == 1) {

                heading3Font.draw(batch, "Orta", 770, y);
            } else {

                heading3Font.draw(batch, "Düşük", 770, y);
            }
        }
        batch.draw(resourceManager.textures.get("menu_lineShort"), 930, 450);
        y = 870;

        for (int i = 0; i < 3; i++) {

            y -= 120;

            if (i == 2) {

                heading3Font.draw(batch, "Genç", 970, y);
            } else if (i == 1) {

                heading3Font.draw(batch, "İş Adamı", 970, y);
            } else {

                heading3Font.draw(batch, "Yaşlı", 970, y);
            }
        }
        batch.draw(resourceManager.textures.get("menu_lineShort"), 1200, 450);
        batch.end();

    }
}
