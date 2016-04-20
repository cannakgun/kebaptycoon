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
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.controller.menuControllers.StaffMenuController;
import com.kebaptycoon.controller.menuControllers.StockMenuController;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by dogancandemirtas on 16/04/16.
 */
public class StaffMenu extends Menu{
    public StaffMenu(GameScreen gameScreen) {

        super(gameScreen);

        MenuController mc = new StaffMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        currentPage = 0;
    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {

    }
}
