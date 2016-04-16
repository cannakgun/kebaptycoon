package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.MenuController;

public abstract class Menu {

    InputProcessor menuController;

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
}
