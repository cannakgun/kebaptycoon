package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.FriendsMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.view.screens.GameScreen;

public class FriendsMenu extends Menu{

    public FriendsMenu(GameScreen gameScreen) {

        super(gameScreen);
        MenuController mc = new FriendsMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        currentPage = 0;
    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {
        batch.begin();
        batch.draw(resourceManager.textures.get("menu_backgrounds_friendsBackground"), 300, 300);
        //heading1Font.draw(batch, Globals.FRIENDS_MENU_TITLE, 815, 920);

        batch.draw(resourceManager.textures.get("friends_can"), 500, 700, 100, 100);
        heading3Font.draw(batch, "Seviye : 3    Günlük gelir : 5000 TL    Acilan yemek sayisi : 4", 650, 750);

        batch.draw(resourceManager.textures.get("friends_sinem"), 500, 590, 100, 100);
        heading3Font.draw(batch, "Seviye : 3    Günlük gelir : 1200 TL    Acilan yemek sayisi : 5", 650, 650);

        batch.draw(resourceManager.textures.get("friends_kivanc"), 500, 480, 100, 100);
        heading3Font.draw(batch, "Seviye : 3    Günlük gelir : 300 TL    Acilan yemek sayisi :14", 650, 550);

        batch.draw(resourceManager.textures.get("friends_ulas"), 500, 370, 100, 100);
        heading3Font.draw(batch, "Seviye : 3    Günlük gelir : 800 TL    Acilan yemek sayisi : 2", 650, 450);
        batch.end();
    }
}
