package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.EstateMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.view.screens.GameScreen;

public class EstateMenu extends Menu {

    public EstateMenu(GameScreen gameScreen) {

        super(gameScreen);
        MenuController mc = new EstateMenuController(gameScreen, this);
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
        batch.draw(resourceManager.textures.get("menu_backgrounds_estateBackground"), 300, 300);

        //heading1Font.draw(batch, Globals.ESTATE_MENU_TITLE, 845, 920);
        batch.end();
    }

}
