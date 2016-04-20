package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.logic.GameLogic;
import com.kebaptycoon.utils.ResourceManager;
import com.kebaptycoon.view.screens.GameScreen;

public abstract class Menu {

    InputProcessor menuController;
    GameLogic gameLogic;
    GameScreen gameScreen;
    ResourceManager resourceManager;
    FreeTypeFontGenerator heading1Generator;
    FreeTypeFontGenerator.FreeTypeFontParameter heading1Parameter;
    FreeTypeFontGenerator heading2Generator;
    FreeTypeFontGenerator.FreeTypeFontParameter heading2Parameter;
    BitmapFont heading1Font, heading2Font;
    int currentPage;

    public Menu(){
        Gdx.input.setCatchBackKey(true);
    }

    public abstract void render(SpriteBatch batch, Viewport viewPort);

    public InputProcessor getMenuController() {
        return menuController;
    }

    public void setMenuController(InputProcessor menuController) {
        this.menuController = menuController;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void changeCurrentPage(int delta) {}
}
